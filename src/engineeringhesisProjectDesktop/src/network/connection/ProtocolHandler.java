package network.connection;

import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import controllers.Keyboard;
import controllers.Mouse;

import network.protocol.KeyboardProtocol;
import network.protocol.MouseProtocol;
import network.protocol.Protocol;
import network.protocol.StreamProtocol;

public class ProtocolHandler {

	private ObjectInputStream in;
	private OutputStream out;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public ProtocolHandler(ObjectInputStream in, OutputStream out) throws AWTException{
		mouse = new Mouse();
		keyboard = new Keyboard();
		this.in = in;
		this.out = out;
	}
	
	public void handleFrame(byte frame){
		switch(Protocol.getController(frame)){
		case Protocol.MOUSE:
			handleMouseProtocol(frame);
			break;
		case Protocol.KEYBOARD:
			handleKeyboardProtocol(frame);
			break;
		case Protocol.STREAM:
			handleStreamProtocol(frame);
			break;
		case Protocol.HANDSHAKE_TO:
			handleHandshake();
			break;
		}
	}
	
	private void handleHandshake() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					out.write(Protocol.HANDSHAKE_FROM);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	private void handleStreamProtocol(byte frame){
		System.out.println("stream");
		if(StreamProtocol.isStartStream(frame)){
			try {
				//TODO ilsoc klatek z ustawien
				Thread th = new Thread(new StreamSender(out, 100));
				th.start();

				System.out.println("start");
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("start nieudany");
			}
			
		}
	}
	
	private void handleMouseProtocol(byte frame){
		if(mouse == null){
			//TODO
			//to co przy klawiaturze gdy null
		}
		if(MouseProtocol.isZeroIntModeType(frame)) {
			if(MouseProtocol.isWheelMode(frame)){
				try {
					int val = (int) in.readObject();
					mouse.moveWheel(val);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				//nothing to read enough info
				System.out.println("zero mode");
				try{
					mouse.doClick(frame);
				} catch(IllegalArgumentException e){
					System.out.println("chujowo "+e.getMessage());
				}
			}
		} else {
			int coordX;
			int coordY;
			try {
				coordX = (int) in.readObject();
				coordY = (int) in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO znowu obsluga bledow
				e.printStackTrace();
				return;
			}
			
			System.out.println("two int mode "+coordX+" "+coordY);
			mouse.makeMovement(frame, coordX, coordY);
		}
	}
	private void handleKeyboardProtocol(byte frame){
		if(keyboard == null){
			//TODO
			//proponowalbym dorzucic do gui log i tam wyswietlac lub okienko
		}
		try{
			if(KeyboardProtocol.isSingleKeyMode(frame)){
				int key = (int) in.readObject();
				keyboard.doClick(frame, key);
			} else {
				// multiple keys were pressed
				int size = (int) in.readObject();
				int[] array = new int[size];
				for(int i=0; i < size; i++){
					array[i] = (int) in.readObject();
				}
				keyboard.doClick(frame, array);
			}
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
}
