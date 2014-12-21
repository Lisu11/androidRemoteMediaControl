package engineering_thesis_project.android.network.manager;

import java.io.IOException;
import java.io.ObjectOutputStream;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BluetoothManager {
	private static BluetoothManager instance = null;

	private static BluetoothSocket socket;
	private static BluetoothDevice device;
	private ObjectOutputStream out;

	private BluetoothManager() throws IllegalAccessError, IOException {
		if (socket == null || device == null)
			throw new IllegalAccessError();
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public static BluetoothSocket getSocket() {
		return socket;
	}

	public static BluetoothDevice getDevice() {
		return device;
	}

	public static void setDevice(BluetoothDevice device) {
		BluetoothManager.device = device;
	}

	public static void setSocket(BluetoothSocket socket) {
		BluetoothManager.socket = socket;
	}

	public static void nullInstance() {
		instance = null;
		socket = null;
		device = null;
	}

	public static BluetoothManager getInstance() throws IllegalAccessError,
			IOException {
		if (instance == null) {
			instance = new BluetoothManager();
		}
		return instance;
	}

	public ObjectOutputStream getOut(){
		return out;
	}
	
	synchronized public void sendFrame(byte frame) throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new BluetoothSender(frame).execute();
	}

	synchronized public void sendFrames(int[] list) throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new BluetoothSender(list).execute();
	}

	synchronized public void sendFrames(byte frame, int arg1, int arg2)
			throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		new BluetoothSender(frame, arg1, arg2).execute();
	}

	synchronized public void sendFrames(byte frame, int arg1)
			throws IOException {
		if (socket == null) {
			throw new IOException("could not connect with");
		}
		Log.i("before sender constructor", "frame="+frame+" arg="+arg1);
		new BluetoothSender(frame, arg1).execute();
	}
}
