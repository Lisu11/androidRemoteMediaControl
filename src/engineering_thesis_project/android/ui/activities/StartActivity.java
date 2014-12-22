package engineering_thesis_project.android.ui.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.fragments.BluetoothSetupFragment_;
import engineering_thesis_project.android.ui.fragments.MyFragment;
import engineering_thesis_project.android.ui.fragments.WiFiSetupFragment_;

@EActivity(R.layout.activity_start)
public class StartActivity extends Activity {

	MyFragment fragment1 = new WiFiSetupFragment_();
	MyFragment fragment2 = new BluetoothSetupFragment_();

	@ViewById(R.id.fragmentContainer)
	RelativeLayout fragmentContainer;
	@ViewById(R.id.fragmentContainer2)
	RelativeLayout fragmentContainer2;

	@ViewById(R.id.radioButtonWiFi)
	RadioButton wifiButton;
	@ViewById(R.id.radioButtonBluetooth)
	RadioButton bluetoothButton;

	boolean portraitOrientation;

	@Override protected void onRestoreInstanceState(Bundle savedInstanceState) { 
		super.onRestoreInstanceState(savedInstanceState);
		portraitOrientation = getResources().getDisplayMetrics().widthPixels < getResources()
				.getDisplayMetrics().heightPixels;
		if (portraitOrientation && 
				savedInstanceState.get(Constants.PREV_VIEW) == null) { 
			// display wifi configuration fragment by default 
			wifiButton.setChecked(true);
	  } else if(portraitOrientation){
		  fragment1 = getNewFragment((Integer) savedInstanceState.get(Constants.PREV_VIEW));
		  
	  } 
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (portraitOrientation)
			outState.putInt(Constants.PREV_VIEW, fragment1.getFragmentType());
	}

	@AfterViews
	public void init() {
		portraitOrientation = getResources().getDisplayMetrics().widthPixels < getResources()
				.getDisplayMetrics().heightPixels;
		
		
		getFragmentManager().beginTransaction()
				.replace(R.id.fragmentContainer, fragment1).commit();
		if (portraitOrientation) {
			wifiButton
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								fragment1 = new WiFiSetupFragment_();
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.fragmentContainer,
												fragment1).commit();
							}
						}
					});
			bluetoothButton
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								fragment1 = new BluetoothSetupFragment_();
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.fragmentContainer,
												fragment1).commit();
							}
						}
					});
		} else {
			getFragmentManager().beginTransaction()
					.replace(R.id.fragmentContainer2, fragment2).commit();
		}
	}

	private MyFragment getNewFragment(int fragNr) {
		switch (fragNr) {
		case Constants.WIFI:
			wifiButton.setChecked(true);
			return new WiFiSetupFragment_();
		case Constants.BLUETOOTH:
			bluetoothButton.setChecked(true);
			return new BluetoothSetupFragment_();
		default:
			return null;
		}
	}

	public void disableSwipe() {
		fragmentContainer.setOnTouchListener(null);
		// bluetoothButton.setEnabled(false);
		// wifiButton.setEnabled(false);
	}
}
