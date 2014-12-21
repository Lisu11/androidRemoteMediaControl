package engineering_thesis_project.android.network.manager;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;

public class NetworkManager {
	private static String SERVER_IP = "156.17.234.187";
	private static int PORT = 1111;

	private static NetworkManager instance = null;
	private static Socket socket = null;
	private ObjectOutputStream out;
	DataInputStream dis;

	private NetworkManager() {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Log.v("connectiong with server", "IP="+SERVER_IP+" port="+PORT);
					socket = new Socket(SERVER_IP, PORT);
					Log.v("connection with server ", "succeeded");
					out = new ObjectOutputStream(socket.getOutputStream());
				} catch (IOException e) {
					Log.e("Cannot connect with server or create out", "");
				}
			}
		});
		th.start();
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	public static void nullInstance(){
		instance = null;
		socket = null;
	}
	
	synchronized public void sendFrame(byte frame) throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new Sender(frame).execute();
	}

	synchronized public void sendFrames(int[] list) throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new Sender(list).execute();
	}

	synchronized public void sendFrames(byte frame, int arg1, int arg2)
			throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new Sender(frame, arg1, arg2).execute();
	}

	synchronized public void sendFrames(byte frame, int arg1)
			throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		Log.i("before sender constructor", "frame="+frame+" arg="+arg1);
		new Sender(frame, arg1).execute();
	}

	
	public Socket getSocket() {
		return socket;
	}

	public static String getSERVER_IP() {
		return SERVER_IP;
	}

	public static void setSERVER_IP(String sERVER_IP) {
		SERVER_IP = sERVER_IP;
	}

	public static int getPORT() {
		return PORT;
	}

	public static void setPORT(int pORT) {
		PORT = pORT;
	}
}
