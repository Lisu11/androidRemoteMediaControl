package network.connection;
import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import network.protocol.KeyboardProtocol;
import network.protocol.MouseProtocol;
import network.protocol.Protocol;
import network.protocol.StreamProtocol;
import controllers.Keyboard;
import controllers.Mouse;


public class Server extends Thread {

	private ServerSocket serversocket;
	private Socket socket;
	private ObjectInputStream in;
	private OutputStream out;
	private Mouse mouse;
	private Keyboard keyboard;
	
	public Server(int port) throws IOException, AWTException {
		serversocket = new ServerSocket(port);
		System.out.println("socket utworzony");
		mouse = new Mouse();
		keyboard = new Keyboard();
		
	}

	private void InOutSetup() throws IOException {
		out = socket.getOutputStream();
		System.out.println("out poszed\n");

		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("in poszed\n");
	}

	@SuppressWarnings("deprecation")
	public void finish(){
		try {
			in.close();
			out.close();
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		this.stop();
	}
	
	@Override
	public void run() {
		try {
			System.out.println("czekamy na polaczenie");
			socket = serversocket.accept();
			System.out.println("server stoi i polaczony");
			try{
				InOutSetup();
				System.out.println("strumienie zainicjowane");
			}catch(IOException e){
				System.out.println("strumienie niezainicjowane\n"+e.getLocalizedMessage());
				//TODO tu juz napweno okienko z errorem
				return;
			}

			while (true) {
				try {
					System.out.println("Czekam na wiadomosc");
					byte frame =  (byte) in.readObject();
					System.out.println("wiadomosc "+frame);
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
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.out.println("koniec");
					finish();
					break;
				}
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			System.out.println("koniec");
			finish();
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
