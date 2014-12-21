package engineering_thesis_project.android.controlers.remote.media_players;

import engineering_thesis_project.android.ui.activities.R;

public class VLCPlayer extends MediaPlayer{

	public VLCPlayer(){
		super("VLC",R.drawable.player_vlc);
		setShortCuts();
	}
	
	@Override
	protected void setShortCuts() {
		int[] t1 ={VK_SHIFT, VK_LEFT};
		shortCuts.put(back, t1);
		int[] t2 ={VK_SHIFT, VK_RIGHT};
		shortCuts.put(forward, t2);
		int[] t3 ={VK_SPACE};
		shortCuts.put(play, t3);
		int[] t4 ={VK_S};
		shortCuts.put(stop, t4);
		int[] t5 = {VK_CONTROL, VK_RIGHT};
		shortCuts.put(fastFor, t5);
		int[] t6 ={VK_CONTROL, VK_LEFT};
		shortCuts.put(fastBack, t6);
		int[] t7 ={VK_N};
		shortCuts.put(next, t7);
		int[] t8 ={VK_P};
		shortCuts.put(prev, t8);
		int[] t9 ={VK_CONTROL, VK_DOWN};
		shortCuts.put(minus, t9);
		int[] t10 ={VK_CONTROL, VK_UP};
		shortCuts.put(plus, t10);
		int[] t11 ={VK_F};
		shortCuts.put(full, t11);
		int[] t12 ={VK_M};
		shortCuts.put(mute, t12);
	}

}
