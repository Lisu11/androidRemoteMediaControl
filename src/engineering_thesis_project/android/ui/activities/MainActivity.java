package engineering_thesis_project.android.ui.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import engineering_thesis_project.android.network.manager.BluetoothSender;
import engineering_thesis_project.android.network.manager.ConnectionManager;
import engineering_thesis_project.android.network.manager.Sender;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.statics.Settings;
import engineering_thesis_project.android.ui.fragments.KeyboardFragment_;
import engineering_thesis_project.android.ui.fragments.MyFragment;
import engineering_thesis_project.android.ui.fragments.RemoteFragment_;
import engineering_thesis_project.android.ui.fragments.StreamFragment_;
import engineering_thesis_project.android.ui.fragments.TouchpadFragment_;
import engineering_thesis_project.android.ui.navigation_menu.MyAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	MyFragment fragment;
	int connectionType = 222;

	@ViewById(R.id.drawerLaout)
	public DrawerLayout drawerLyout;
	@ViewById(R.id.drawerListLeft)
	ListView contrListView;
	@ViewById(R.id.drawerListRight)
	public ListView settingsListView;

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		if (savedInstanceState.get(Constants.PREV_VIEW) == null) {
			// display touchpad fragment by default
			displayView(Constants.MOUSE);
		} else {
			displayView((Integer) savedInstanceState.get(Constants.PREV_VIEW));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.PREV_VIEW, fragment.getFragmentType());
	}

	@AfterViews
	public void init() {
		if (getIntent().getExtras().getInt(Constants.CONNECTION_TYPE) == Constants.BLUETOOTH){
			new ConnectionManager(false);
			connectionType = Constants.BLUETOOTH;
		} else if (getIntent().getExtras().getInt(Constants.CONNECTION_TYPE) == Constants.WIFI){
			new ConnectionManager(true);
			connectionType = Constants.WIFI;
		} else {
			//wrong code were in bundle
			Log.e("Main Activity Line 73", "Wrong code connection type in bundle");
			finish();
		}
		// init sender context
		if(connectionType == Constants.BLUETOOTH){
			BluetoothSender.setContext(MainActivity.this);
		} else {
			Sender.setContext(MainActivity.this);
		}
		// init settings class
		Settings.getSettings(this);
		if (fragment == null) {
			// display touchpad fragment by default
			displayView(Constants.MOUSE);
		}
		initLeftList();
	}

	private void initLeftList() {
		contrListView.setAdapter(new MyAdapter(getApplicationContext(),
				R.array.controllers_names, R.array.controllers_icons));
		contrListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (connectionType == Constants.BLUETOOTH
						&& Constants.STREAM == position) {
					Toast.makeText(getApplicationContext(),
							R.string.bluetooth_stream, Toast.LENGTH_SHORT)
							.show();
				} else {
					displayView(position);
				}
				drawerLyout.closeDrawer(Gravity.START);
			}
		});
	}

	private void displayView(int controler) {
		fragment = getNewFragment(controler);

		if (fragment != null) {
			FragmentManager frMan = getFragmentManager();
			frMan.beginTransaction().replace(R.id.mainContent, fragment)
					.commit();
		} else {
			Log.e("Spieprzylo sie", "podales zly nr fragmentu do wyswietlenia");
		}
	}

	private MyFragment getNewFragment(int fragNr) {
		switch (fragNr) {
		case Constants.MOUSE:
			return new TouchpadFragment_();
		case Constants.KEYBOARD:
			return new KeyboardFragment_();
		case Constants.STREAM:
			return new StreamFragment_();
		case Constants.REMOTE:
			return new RemoteFragment_();
		default:
			Toast.makeText(getApplicationContext(), "Not supported yet",
					Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	public void askForRestart() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle(R.string.connection_lost)
				.setMessage(R.string.restart_app)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						restartApplication();
					}
				})
				.setNegativeButton(android.R.string.no, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// nothing
					}
				}).create().show();
	}

	private void restartApplication() {
		Intent i = getBaseContext().getPackageManager()
				.getLaunchIntentForPackage(getBaseContext().getPackageName());
		ConnectionManager.nullInstances();
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		overridePendingTransition(R.anim.in_right, R.anim.out_left);
		MainActivity.this.finish();
	}
}
