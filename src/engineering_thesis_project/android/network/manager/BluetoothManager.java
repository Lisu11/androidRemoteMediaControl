package engineering_thesis_project.android.network.manager;

public class BluetoothManager {
	private static BluetoothManager instance =null;
	
	private BluetoothManager(){
		
	}
	
	public static BluetoothManager getInstance(){
		if(instance == null){
			instance = new BluetoothManager();
		}
		return instance;
	}


}
