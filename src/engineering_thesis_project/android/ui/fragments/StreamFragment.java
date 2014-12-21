package engineering_thesis_project.android.ui.fragments;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import engineering_thesis_project.android.controlers.mouse.MyGestureDetector;
import engineering_thesis_project.android.controlers.mouse.Touchpad;
import engineering_thesis_project.android.network.manager.NetworkManager;
import engineering_thesis_project.android.network.manager.Receiver;
import engineering_thesis_project.android.network.protocol.Protocol;
import engineering_thesis_project.android.network.protocol.StreamProtocol;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.statics.Settings;
import engineering_thesis_project.android.ui.activities.MainActivity;
import engineering_thesis_project.android.ui.activities.R;
import engineering_thesis_project.android.ui.navigation_menu.MyAdapter;

@EFragment(R.layout.fragment_stream)
public class StreamFragment extends MyFragment {

	Touchpad mouse;
	MyGestureDetector gestureDetector;
	boolean enableStream;
	BlockingQueue<byte[]> queue;
	int frameRate = 350;

	@ViewById(R.id.streamImageView)
	ImageView streamImage;

	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {
		@Override
		public void run() {
			reciveFrame();
			if (enableStream) {
				timerHandler.postDelayed(this, frameRate);
			}
		}
	};

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.STREAM;
		Receiver.video = streamImage;
		Receiver.socket = NetworkManager.getInstance().getSocket();

		mouse = new Touchpad(Settings.getMouseSensitivity());
		gestureDetector = new MyGestureDetector(getActivity(), mouse);
		enableStream = false;//stream disabled by default
		streamImage.setClickable(Settings.isStreamEnableTouchpad());
		frameRate = Settings.getStreamFrameRate();
		streamImage.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});

		initSettingsList();
	}

	private void reciveFrame() {

		try {
			NetworkManager.getInstance().sendFrame(
					(byte) (Protocol.STREAM | StreamProtocol.START_STREAM));
			new Receiver().execute();
		} catch (IOException e) {
			Log.e("Something goes wrong", "Could not request for new frame "
					+ e.getMessage());
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		timerHandler.removeCallbacks(timerRunnable);
	}

	private void initSettingsList() {
		MyAdapter adapter = new MyAdapter(getActivity(),
				R.array.stream_settings_names, R.array.stream_settings_icons);

		((MainActivity) getActivity()).settingsListView.setAdapter(adapter);
		((MainActivity) getActivity()).settingsListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 1:// enable stream
							togglestream();
							break;
						case 2:// frame rate
							//TODO frame rate do streama
							/*
							 * trzeba:
							 * - wlaczyc w navigation drawerrze
							 * - no jakos usprawnic caly stream
							 * - zapisywanie i odczytywanie jest
							 */
							Toast.makeText(getActivity(), "Not supported yet",
									Toast.LENGTH_SHORT).show();
							break;
						case 3:// enable touchpad
							toggleTouch();
							break;
						case 4:// save settings
							saveSettings();
							break;
						default:
							break;
						}
						((MainActivity) getActivity()).drawerLyout
								.closeDrawer(Gravity.END);// close drawer
															// after click
					}
				});
	}

	private void saveSettings(){
		Settings.setStreamEnableTouchpad(streamImage.isClickable());
		Settings.setStreamFrameRate(frameRate);
		Settings.saveSettings(getActivity());
		Toast.makeText(getActivity(), R.string.settings_saved,
				Toast.LENGTH_SHORT).show();
	}
	
	private void toggleTouch() {
		if(streamImage.isClickable()){
			Toast.makeText(getActivity(), R.string.settings_stream_touch_disabled,
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), R.string.settings_stream_touch_enabled,
					Toast.LENGTH_SHORT).show();
		}
		
		streamImage.setClickable(!streamImage.isClickable());
	}

	private void togglestream() {
		if (!enableStream) {
			Toast.makeText(getActivity(), R.string.settings_stream_enabled,
					Toast.LENGTH_SHORT).show();
			timerHandler.postDelayed(timerRunnable, 0);
		} else {
			Toast.makeText(getActivity(), R.string.settings_stream_disabled,
					Toast.LENGTH_SHORT).show();
		}
		enableStream = !enableStream;
	}

	// TODO 2 tryb myszki czyli ustaw w miejscu i kiknij
	/*
	 * zeby to zrobic brakuje:
	 * - metod w listenerze
	 * - opcji wlaczania tego trybu w navigation drawer
	 * - no i zeby to mialo sens to trzeba zooma zrobic
	 * 		ale moze byc sporo klopotow z refreshem
	 * 
	 */
}
