package engineering_thesis_project.android.ui.fragments;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.activities.MainActivity_;
import engineering_thesis_project.android.ui.activities.R;

@EFragment(R.layout.fragment_bluetooth_setup)
public class BluetoothSetupFragment extends MyFragment implements
		OnItemClickListener, OnCheckedChangeListener {

	private final static int REQ_BT_ENABLE = 1;

	@ViewById(R.id.discoverButton)
	Button discover;
	@ViewById(R.id.devicesListView)
	ListView listview;
	@ViewById(R.id.switchBlurtooth)
	Switch toggleBT;
	@ViewById(R.id.progressBar)
	ProgressBar progress;

	private ArrayAdapter<String> adapter;
	private BluetoothAdapter btAdapter;
	private BroadcastReceiver receiver;
	private ArrayList<BluetoothDevice> detectedDevices;
	private Set<BluetoothDevice> pairedDevices;

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.BLUETOOTH;
		progress.setVisibility(View.INVISIBLE);
		toggleBT.setOnCheckedChangeListener(this);
		listview.setOnItemClickListener(this);
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, 0);
		listview.setAdapter(adapter);
		detectedDevices = new ArrayList<BluetoothDevice>();
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter == null) {
			deviceWithoutBT();
		}
		if (btAdapter.isEnabled()) {
			enableComponents(true);
			toggleBT.setChecked(true);
		} else {
			enableComponents(false);
			toggleBT.setChecked(false);
		}
		initReceiver();
	}

	private void connect(){
		Intent intent = new Intent(getActivity(), MainActivity_.class);
		intent.putExtra(Constants.CONNECTION_TYPE, Constants.BLUETOOTH);
		startActivity(intent);
		getActivity().finish();
	}
	
	@Click(R.id.discoverButton)
	public void discoverDevices() {
		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}

		detectedDevices = new ArrayList<BluetoothDevice>();
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, 0);
		listview.setAdapter(adapter);

		pairedDevices = btAdapter.getBondedDevices();
		btAdapter.startDiscovery();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_BT_ENABLE) {
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(getActivity(), R.string.enabled_BT,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getActivity(), R.string.BT_error,
						Toast.LENGTH_LONG).show();
				enableComponents(false);
				toggleBT.setChecked(false);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}
		progress.setVisibility(ProgressBar.INVISIBLE);
		if (pairedDevices != null
				&& pairedDevices.contains(detectedDevices.get(position))) {
			//device is alredy paired
			displayAlertDialogForPaired(detectedDevices.get(position));
		} else {
			displayAlertDialogForUnpaired(detectedDevices.get(position));
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// on click for toggle
		if (isChecked) {
			turnBTOn();
			enableComponents(true);
		} else {
			enableComponents(false);
			turnBTOff();
		}
	}

	private void displayAlertDialogForUnpaired(final BluetoothDevice device){
		// device has not been paired yet
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.unpared_dev_title)
			.setMessage(R.string.unpared_dev_message)
			.setPositiveButton(android.R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if ( pairDevice(device) ) {
							//nothing
						} else {
							Toast.makeText(getActivity(),
									R.string.pair_error, Toast.LENGTH_SHORT)
									.show();
						}
					}
				})
			.setNegativeButton(android.R.string.no, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// nothing to do here
					}
			}).show();
	}
	
	private void displayAlertDialogForPaired(final BluetoothDevice device){
		new AlertDialog.Builder(getActivity())
		.setTitle(R.string.pared_dev_title)
		.setMessage(R.string.pared_dev_message)
		.setNeutralButton(R.string.unpair_button, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				if ( unpairDevice(device) ) {
					//pairedDevices.remove(device);
					//nothing
				} else {
					Toast.makeText(getActivity(),
							R.string.unpair_error, Toast.LENGTH_SHORT)
							.show();
				}
			}
		}).setPositiveButton(android.R.string.yes, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				//TODO
				//new Connection(devices.get(position)).execute();
			}
		}).setNegativeButton(android.R.string.no, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				// nothing to do here
			}
		}).show();
	}
	
	private void turnBTOn() {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		Toast.makeText(getActivity(), R.string.enabling_BT, Toast.LENGTH_SHORT)
				.show();
		startActivityForResult(intent, REQ_BT_ENABLE);
	}

	private void turnBTOff() {
		Toast.makeText(getActivity(), R.string.disabled_BT, Toast.LENGTH_SHORT)
				.show();
		btAdapter.disable();
	}

	private void enableComponents(boolean value) {
		listview.setEnabled(value);
		discover.setEnabled(value);
	}

	private void deviceWithoutBT() {
		new AlertDialog.Builder(getActivity()).setTitle(R.string.no_BT_title)
				.setMessage(R.string.no_BT_message)
				.setNeutralButton("ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						toggleBT.setEnabled(false);
						listview.setEnabled(false);
						discover.setEnabled(false);
						// finish();
					}
				}).show();
	}

	private boolean pairDevice(BluetoothDevice device) {
		try {
			Method method = device.getClass().getMethod("createBond",
					(Class[]) null);
			method.invoke(device, (Object[]) null);
		} catch (Exception e) {
			Log.e("proba parowania nie opwidla sie", "KURWA");
			return false;
		}
		return true;
	}

	private boolean unpairDevice(BluetoothDevice device) {
		try {
			Method method = device.getClass().getMethod("removeBond",
					(Class[]) null);
			method.invoke(device, (Object[]) null);

		} catch (Exception e) {
			Log.e("proba odparowania nie opwidla sie", "KURWA");
			return false;
		}
		return true;
	}

	private void initReceiver() {
		// TODO
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action = intent.getAction();
				if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
					progress.setVisibility(ProgressBar.VISIBLE);
				} else if (action
						.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
					progress.setVisibility(ProgressBar.INVISIBLE);
				} else if (action.equals(BluetoothDevice.ACTION_FOUND)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					detectedDevices.add(device);

					adapter.add(device.getName() + "\n" + device.getAddress());
				}
			}
		};

		getActivity().registerReceiver(receiver,
				new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
		getActivity().registerReceiver(receiver,
				new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
		getActivity().registerReceiver(receiver,
				new IntentFilter(BluetoothDevice.ACTION_FOUND));
	}
}
