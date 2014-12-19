package engineering_thesis_project.android.network.manager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class ConnectionManager {

	private final boolean connectedViaWiFi;
	public static ConnectionManager instance;
	
	public ConnectionManager(int port, String IP){
		connectedViaWiFi = true;
		NetworkManager.setPORT(port);
		NetworkManager.setSERVER_IP(IP);
		NetworkManager.getInstance();
		instance = this;
	}

	public ConnectionManager(){
		connectedViaWiFi = false;
	}
	
	
	public ObjectOutputStream getOut() {
		if(connectedViaWiFi){
			return NetworkManager.getInstance().getOut();
		} else {
			return null; //TODO
		}	
	}
	
	public Socket getSocket() {
		if(connectedViaWiFi){
			return NetworkManager.getInstance().getSocket();
		} else {
			return null; //TODO
		}	
	}
	
	synchronized public void sendFrame(byte frame) throws IOException {
		if (connectedViaWiFi) {
			NetworkManager.getInstance().sendFrame(frame);
		} else {
			
		}
	}

	synchronized public void sendFrames(int[] list) throws IOException {
		if (connectedViaWiFi) {
			NetworkManager.getInstance().sendFrames(list);
		} else {
			
		}
	}

	synchronized public void sendFrames(byte frame, int arg1, int arg2)
			throws IOException {
		if (connectedViaWiFi) {
			NetworkManager.getInstance().sendFrames(frame, arg1, arg2);
		} else {
			
		}
	}
	
	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	public static String getIpAddr(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(
				android.content.Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		// int ip = wifiManager.getDhcpInfo().ipAddress;
		String ipString = String.format(
				"%d.%d.%d.%d", 
				(ip & 0xff),
				(ip >> 8 & 0xff), 
				(ip >> 16 & 0xff),
				// (ip >> 24 & 0xff)
				1);

		return ipString;
	}

}
