package engineering_thesis_project.android.ui.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import engineering_thesis_project.android.controlers.keyboard.AWTConstants;
import engineering_thesis_project.android.controlers.keyboard.Keyboard;
import engineering_thesis_project.android.controlers.remote.media_players.GomPlayer;
import engineering_thesis_project.android.controlers.remote.media_players.MediaPlayer;
import engineering_thesis_project.android.controlers.remote.media_players.VLCPlayer;
import engineering_thesis_project.android.controlers.remote.media_players.WinampPlayer;
import engineering_thesis_project.android.controlers.remote.media_players.WindowsMediaPlayer;
import engineering_thesis_project.android.controlers.remote.media_players.WrongImplementationException;
import engineering_thesis_project.android.controlers.remote.media_players.YouTubePlayer;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.activities.MainActivity;
import engineering_thesis_project.android.ui.activities.R;
import engineering_thesis_project.android.ui.navigation_menu.MyAdapter;

@EFragment(R.layout.fragment_remote)
public class RemoteFragment extends MyFragment implements AWTConstants {

	Keyboard keyboard;
	MediaPlayer player;

	@ViewById(R.id.playPouseImageView)
	ImageView playPouse;
	@ViewById(R.id.iconImageView)
	ImageView icon;

	private boolean playPouseFlag = true;
	private boolean lockOrientationFlag = true;
	private static int currentView = Constants.REMOTE_VLC;

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.REMOTE;
		keyboard = new Keyboard();

		setPlayer(currentView);
		initSettings();
	}

	private void initSettings() {
		MyAdapter adapter = new MyAdapter(getActivity(),
				R.array.remote_settings_names, R.array.remote_settings_icons);
		((MainActivity) getActivity()).settingsListView.setAdapter(adapter);
		((MainActivity) getActivity()).settingsListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case Constants.REMOTE_VLC:
							setPlayer(Constants.REMOTE_VLC);
							break;
						case Constants.REMOTE_GOM:
							setPlayer(Constants.REMOTE_GOM);
							break;
						case Constants.REMOTE_WINAMP:
							setPlayer(Constants.REMOTE_WINAMP);
							break;
						case Constants.REMOTE_WMP:
							setPlayer(Constants.REMOTE_WMP);
							break;
						case Constants.REMOTE_YOUTUBE:
							setPlayer(Constants.REMOTE_YOUTUBE);
							break;
						default:
							lockOrientation();
							break;
						}
						((MainActivity) getActivity()).drawerLyout
								.closeDrawer(Gravity.END);// close drawer
															// after click
					}

				});
	}

	private void lockOrientation() {
		if (lockOrientationFlag) {
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_LOCKED);
			Toast.makeText(getActivity(), R.string.rotation_locked,
					Toast.LENGTH_SHORT).show();
		} else {
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
			Toast.makeText(getActivity(), R.string.rotation_unlocked,
					Toast.LENGTH_SHORT).show();
		}
		lockOrientationFlag = !lockOrientationFlag;
	}

	@Override
	public void onPause() {
		super.onPause();
		getActivity().setRequestedOrientation(
				ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
	}

	private void setPlayer(int mediaPlayer) {
		switch (mediaPlayer) {
		case Constants.REMOTE_VLC:
			player = new VLCPlayer();
			break;
		case Constants.REMOTE_WINAMP:
			player = new WinampPlayer();
			break;
		case Constants.REMOTE_WMP:
			player = new WindowsMediaPlayer();
			break;
		case Constants.REMOTE_YOUTUBE:
			player = new YouTubePlayer();
			break;
		case Constants.REMOTE_GOM:
			player = new GomPlayer();
			break;
		default:
			break;
		}

		icon.setImageDrawable(getActivity().getResources().getDrawable(
				(player.playerIcon)));
		currentView = mediaPlayer;
	}

	@Click(R.id.playPouseImageView)
	public void playPouseClick() {

		try {
			player.playPouseClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
		if (playPouseFlag) {
			playPouse.setImageDrawable(getActivity().getResources()
					.getDrawable((R.drawable.pouse_selector)));
		} else {
			playPouse.setImageDrawable(getActivity().getResources()
					.getDrawable((R.drawable.play_selector)));
		}
		playPouseFlag = !playPouseFlag;
	}

	@Click(R.id.stopPouseImageView)
	public void stopClick() {
		try {
			player.stopClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.backImageView)
	public void backClick() {
		try {
			player.backClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.forImageView)
	public void forClick() {
		try {
			player.forClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Constants.REMOTE_TAG, currentView);
	}

	@Click(R.id.fastBackImageView)
	public void fastBackClick() {
		try {
			player.fastBackClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.fastForImageView)
	public void fastForClick() {
		try {
			player.fastForClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.nextImageView)
	public void nextClick() {
		try {
			player.nextClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.prevImageView)
	public void prevClick() {
		try {
			player.prevClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.plusImageView)
	public void plusClick() {
		try {
			player.plusClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.minusImageView)
	public void minusClick() {
		try {
			player.minusClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.fullImageView)
	public void fullClick() {
		try {
			player.fullClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}

	@Click(R.id.muteImageView)
	public void muteClick() {
		try {
			player.muteClick(keyboard);
		} catch (WrongImplementationException e) {
			Log.e("WrongImplemantation", e.getMessage());
			return;
		}
	}
}
