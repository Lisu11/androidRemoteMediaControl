package engineering_thesis_project.android.ui.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import engineering_thesis_project.android.controlers.mouse.MyGestureDetector;
import engineering_thesis_project.android.controlers.mouse.Touchpad;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.statics.Settings;
import engineering_thesis_project.android.ui.activities.MainActivity;
import engineering_thesis_project.android.ui.activities.R;
import engineering_thesis_project.android.ui.navigation_menu.MyAdapter;

@EFragment(R.layout.fragment_touchpad)
public class TouchpadFragment extends MyFragment {

	private Touchpad mouse;
	private MyGestureDetector gestureDetector;

	@ViewById(R.id.touchpadImageView)
	ImageView touchpad;

	@ViewById(R.id.leftButtonImageView)
	ImageView leftButton;

	@ViewById(R.id.rightButtonImageView)
	ImageView rightButton;

	/**
	 * Method initializes views gestures etc.
	 * Called after views were inflated 
	 */
	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.MOUSE;
		mouse = new Touchpad(Settings.getMouseSensitivity());
		leftButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mouse.onLeftButtonPressed();
					break;
				case MotionEvent.ACTION_UP:
					mouse.onLeftButtonReleased();
					break;
				}
				return leftButton.onTouchEvent(event); // cause selector has not been invoked yet
			}
		});

		rightButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mouse.onRightButtonPressed();
					break;
				case MotionEvent.ACTION_UP:
					mouse.onRightButtonReleased();
					break;
				}
				return rightButton.onTouchEvent(event); // cause selector has not been invoked yet
			}
		});

		MyGestureDetector
				.setSingleTapEnable(Settings.isMouseEnableTouchClick());
		gestureDetector = new MyGestureDetector(getActivity(), mouse);
		touchpad.setClickable(true);
		touchpad.setOnTouchListener(new View.OnTouchListener() {
			boolean flagaPrssd = false;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				/*
				 * i'm doing this hocus pocus cause
				 * i didn't have mouse dragging gesture
				 * it was caused cause:
				 * after left button was pressed onTouch received
				 * MotionEvent Action_Move from touchpad but when event was passed to
				 * gesture detector method on scroll was not called
				 * don't know why. So I created my own onTouchListener
				 * when i implement this event and method. 
				 */
				if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN 
						&& leftButton.isPressed()){
					gestureDetector.myOnTouchEvent(event);
					flagaPrssd = true;
					return true;
				}
				if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
					if(flagaPrssd){
						gestureDetector.myOnTouchEvent(event);
					} else {
						gestureDetector.onTouchEvent(event);
					}
					flagaPrssd = false;
					return true;
				}
				if(flagaPrssd){
					gestureDetector.myOnTouchEvent(event);
				} else {
					gestureDetector.onTouchEvent(event);
				}
				return true;
			}
		});
		initSettingsList();
	}

	/**
	 * Method initializes navigation drawer 
	 */
	private void initSettingsList() {
		MyAdapter adapter = new MyAdapter(getActivity(),
				R.array.mouse_settings_names, R.array.mouse_settings_icons);

		((MainActivity) getActivity()).settingsListView.setAdapter(adapter);
		((MainActivity) getActivity()).settingsListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 1:// sensitivity
							changeSensitivity();
							break;
						case 2:// enable touch click
							toggleTouchClick();
							break;
						case 3:// enable scroll
							toggleScroll();
							break;
						case 4:// scroll size
							changeScrollSize();
							break;
						case 5:// save settings
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

	/**
	 * Method called after save setting item clicked on navigation drawer
	 * Method saves settings onto JSON file
	 */
	protected void saveSettings() {
		Settings.setMouseEnableTouchClick(MyGestureDetector
				.isSingleTapEnabled());
		Settings.setMouseSensitivity(Touchpad.getSensitivity());
		Settings.setMouseEnableScroll(MyGestureDetector.isSwipeOnEdgeEnabled());
		Settings.saveSettings(getActivity());
		Toast.makeText(getActivity(), R.string.settings_saved,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * Method called after toggle scroll item clicked on navigation drawer
	 * Method toggle scroll bar in the right edge of touchpad
	 */
	protected void toggleScroll() {
		if (MyGestureDetector.isSwipeOnEdgeEnabled()) {
			MyGestureDetector.setSwipeOnEdgeEnabled(false);
			Toast.makeText(getActivity(), R.string.settings_mouse_scroll_dis,
					Toast.LENGTH_SHORT).show();
		} else {
			MyGestureDetector.setSwipeOnEdgeEnabled(true);
			Toast.makeText(getActivity(), R.string.settings_mouse_scroll_en,
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Method called after toggle-touch-click item clicked on navigation drawer
	 * Method toggle click on tap gesture
	 */
	protected void toggleTouchClick() {
		if (MyGestureDetector.isSingleTapEnabled()) {
			Toast.makeText(getActivity(),
					R.string.settings_mouse_single_tap_dis, Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(getActivity(),
					R.string.settings_mouse_single_tap_en, Toast.LENGTH_SHORT)
					.show();
		}
		MyGestureDetector.setSingleTapEnable(!MyGestureDetector
				.isSingleTapEnabled());

	}

	/**
	 * Method called after change-scroll-size item clicked on navigation drawer
	 * Method displays dialog where user can change scroll bar size
	 */
	protected void changeScrollSize() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.seekbar_dialog, null);
		new AlertDialog.Builder(getActivity()).setView(layout)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), R.string.settings_mouse_scroll_size,
								Toast.LENGTH_SHORT).show();
					}
				}).create().show();
		final SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBarDialog);
		sb.setMax(22);
		sb.setProgress(MyGestureDetector.getScrollSize());
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// nothing
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// nothing
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				final int MIN = 7;
				if(progress < MIN)
					sb.setProgress(MIN);
				MyGestureDetector.setScrollSize(progress);
			}
		});
	}

	/**
	 * Method called after change-sensitivity item clicked on navigation drawer
	 * Method displays dialog where user can change mouse sensitivity
	 */
	protected void changeSensitivity() {
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.seekbar_dialog, null);
		new AlertDialog.Builder(getActivity()).setView(layout)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), R.string.settings_mouse_sensitivity,
								Toast.LENGTH_SHORT).show();
					}
				}).create().show();
		SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBarDialog);
		sb.setMax(100);
		sb.setProgress((int) (Touchpad.getSensitivity() * 10));
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// nothing
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// nothing
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Touchpad.setSensitivity(progress / 10);
			}
		});
	}

}
