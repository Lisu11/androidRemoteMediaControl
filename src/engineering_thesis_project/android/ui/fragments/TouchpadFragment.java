package engineering_thesis_project.android.ui.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

	Touchpad mouse;
	MyGestureDetector gestureDetector;

	@ViewById(R.id.touchpadImageView)
	ImageView touchpad;

	@ViewById(R.id.leftButtonImageView)
	ImageView leftButton;

	@ViewById(R.id.rightButtonImageView)
	ImageView rightButton;

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.MOUSE;
		mouse = new Touchpad(Settings.getMouseSensitivity());
		leftButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mouse.onLeftButtonPressed();
				case MotionEvent.ACTION_UP:
					mouse.onLeftButtonReleased();
				}
				return false; // cause selector has not been invoked yet
			}
		});

		rightButton.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mouse.onRightButtonPressed();
				case MotionEvent.ACTION_UP:
					mouse.onRightButtonReleased();
				}
				return false; // cause selector has not been invoked yet
			}
		});

		MyGestureDetector
				.setSingleTapEnable(Settings.isMouseEnableTouchClick());
		gestureDetector = new MyGestureDetector(getActivity(), mouse);
		touchpad.setClickable(true);
		touchpad.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
		initSettingsList();
	}

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

	protected void saveSettings() {
		Settings.setMouseEnableTouchClick(MyGestureDetector
				.isSingleTapEnabled());
		Settings.setMouseSensitivity(Touchpad.getSensitivity());
		Settings.setMouseEnableScroll(MyGestureDetector.isSwipeOnEdgeEnabled());
		Settings.saveSettings(getActivity());
		Toast.makeText(getActivity(), R.string.settings_saved,
				Toast.LENGTH_SHORT).show();
	}

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
