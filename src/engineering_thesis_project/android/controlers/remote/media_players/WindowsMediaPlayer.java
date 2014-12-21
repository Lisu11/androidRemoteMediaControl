package engineering_thesis_project.android.controlers.remote.media_players;

import engineering_thesis_project.android.ui.activities.R;

public class WindowsMediaPlayer extends MediaPlayer {

	public WindowsMediaPlayer(){
		super("Windows Media Player", R.drawable.player_windows);
		setShortCuts();
	}
	
	@Override
	protected void setShortCuts() {
		int[] t1 ={VK_CONTROL, VK_SHIFT, VK_B};
		shortCuts.put(back, t1);
		int[] t2 ={VK_CONTROL, VK_SHIFT, VK_F};
		shortCuts.put(forward, t2);
		int[] t3 ={VK_CONTROL, VK_P};
		shortCuts.put(play, t3);
		int[] t4 ={VK_CONTROL, VK_S};
		shortCuts.put(stop, t4);
		int[] t5 ={VK_CONTROL, VK_SHIFT, VK_G};
		shortCuts.put(fastFor, t5);
		int[] t6 ={VK_CONTROL, VK_SHIFT, VK_S};
		shortCuts.put(fastBack, t6);
		int[] t7 ={VK_CONTROL, VK_F};
		shortCuts.put(next, t7);
		int[] t8 ={VK_CONTROL, VK_B};
		shortCuts.put(prev, t8);
		int[] t9 ={VK_F8};
		shortCuts.put(minus, t9);
		int[] t10 ={VK_F9};
		shortCuts.put(plus, t10);
		int[] t11 ={VK_ALT, VK_ENTER};
		shortCuts.put(full, t11);
		int[] t12 ={VK_F7};
		shortCuts.put(mute, t12);
	}
}
