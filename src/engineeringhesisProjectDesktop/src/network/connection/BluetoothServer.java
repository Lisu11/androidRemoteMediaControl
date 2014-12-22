package network.connection;

import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class BluetoothServer extends Thread{

	StreamConnection connection = null;
	LocalDevice local = null;
	StreamConnectionNotifier notifier;
	UUID uuid;
	ObjectInputStream inputStream;
	ProtocolHandler handler;
	
	public BluetoothServer() {
	}
	
	@Override
	public void run() {
		waitForConnection();
		
		try {
			System.out.println("Inicjuje strumien");
			inputStream = new ObjectInputStream(connection.openInputStream());
			System.out.println("Udalo sie zainicjowac");
			handler = new ProtocolHandler(inputStream, null);
			
			while (true) {
				try {
					System.out.println("Czekam na wiadomosc");
					byte frame =  (byte) inputStream.readObject();
					System.out.println("wiadomosc "+frame);
					handler.handleFrame(frame);
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.out.println("koniec");
					finish();
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		} catch (AWTException e1) {
			e1.printStackTrace();
			finish();
		}
	}

	private void waitForConnection() {
		try {//setup
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);

			uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
			System.out.println(uuid.toString());

			String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}

		 try {//wait for connection
			connection = notifier.acceptAndOpen();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
		
	}

	@SuppressWarnings("deprecation")
	public void finish(){
		
		try {
			notifier.close();
			connection.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		stop();
	}
	
}
