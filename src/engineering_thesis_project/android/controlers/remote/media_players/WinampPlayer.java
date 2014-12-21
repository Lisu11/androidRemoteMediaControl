package engineering_thesis_project.android.controlers.remote.media_players;

import engineering_thesis_project.android.ui.activities.R;

public class WinampPlayer extends MediaPlayer {

	public WinampPlayer(){
		super("Winamp", R.drawable.player_winamp);
		setShortCuts();
	}
	
	@Override
	protected void setShortCuts() {
		int[] t1 ={VK_NUMPAD7};
		shortCuts.put(back, t1);
		int[] t2 ={VK_NUMPAD9};
		shortCuts.put(forward, t2);
		int[] t3 ={VK_C};
		shortCuts.put(play, t3);
		int[] t4 ={VK_V};
		shortCuts.put(stop, t4);
		int[] t5 ={VK_NUMPAD3};
		shortCuts.put(fastFor, t5);
		int[] t6 ={VK_NUMPAD1};
		shortCuts.put(fastBack, t6);
		int[] t7 ={VK_B};
		shortCuts.put(next, t7);
		int[] t8 ={VK_Z};
		shortCuts.put(prev, t8);
		int[] t9 ={VK_DOWN};
		shortCuts.put(minus, t9);
		int[] t10 ={VK_UP};
		shortCuts.put(plus, t10);
		int[] t11 ={VK_ALT, VK_ENTER};
		shortCuts.put(full, t11);
		int[] t12 ={VK_DOWN};
		shortCuts.put(mute, t12);
	}
}
