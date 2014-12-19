package engineering_thesis_project.android.ui.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.fragments.BluetoothSetupFragment_;
import engineering_thesis_project.android.ui.fragments.MyFragment;
import engineering_thesis_project.android.ui.fragments.WiFiSetupFragment_;

@EActivity(R.layout.activity_start)
public class StartActivity extends Activity {

	GestureDetector gestures;
	MyFragment fragment;
	
	@ViewById(R.id.fragmentContainer)
	RelativeLayout fragmentContainer;
	
	@ViewById(R.id.radioButtonWiFi)
	RadioButton wifiButton;
	@ViewById(R.id.radioButtonBluetooth)
	RadioButton bluetoothButton;
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		if(savedInstanceState.get(Constants.PREV_VIEW) == null){
			// display wifi configuration fragment by default
			displayView(Constants.WIFI);
		}	else {
			displayView((Integer) savedInstanceState.get(Constants.PREV_VIEW));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.PREV_VIEW, fragment.getFragmentType());
	}
	
	@AfterViews
	public void init(){
		if(fragment == null){
			// display wifi configuration fragment by default
			displayView(Constants.WIFI);
		}

		wifiButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(Constants.WIFI);
			}
		});

		bluetoothButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(Constants.BLUETOOTH);
			}
		});

		gestures = new GestureDetector(getApplicationContext(), new OnGestureListener() {	
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			@Override
			public void onShowPress(MotionEvent e) {
			}
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				if(Math.abs(distanceX) > Math.abs(distanceY)){
					if(distanceX > 0){
						//right swipe gesture
						bluetoothButton.setChecked(true);
						bluetoothButton.callOnClick();
					} else {
						//left swipe gesture
						wifiButton.setChecked(true);
						wifiButton.callOnClick();
					}
				}
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		} );
		
		fragmentContainer.setClickable(true);
		fragmentContainer.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestures.onTouchEvent(event);
			}
		});
	}

	private void displayView(int connection){
		fragment = getNewFragment(connection);
		if(connection == Constants.WIFI){
			wifiButton.setChecked(true);
		} else  {
			bluetoothButton.setChecked(true);
		}

		if(fragment != null) {
			FragmentManager frMan = getFragmentManager();
			frMan.beginTransaction()
				 .replace(R.id.fragmentContainer, fragment)
				 .commit();
		} else {
			Log.e("Spieprzylo sie", "podales zly nr fragmentu do wyswietlenia");
		}
	}

	private MyFragment getNewFragment(int fragNr){
		switch(fragNr){
		case Constants.WIFI:
			return new WiFiSetupFragment_();
		case Constants.BLUETOOTH:
			return new BluetoothSetupFragment_();
		default:
			return null;
		}
	}
	

	
	public void disableSwipe() {
		fragmentContainer.setOnTouchListener(null);
		bluetoothButton.setEnabled(false);
		wifiButton.setEnabled(false);
	}
}
