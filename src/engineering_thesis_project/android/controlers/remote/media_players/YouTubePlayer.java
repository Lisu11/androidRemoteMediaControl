package engineering_thesis_project.android.controlers.remote.media_players;

import engineering_thesis_project.android.ui.activities.R;

public class YouTubePlayer extends MediaPlayer {

	public YouTubePlayer(){
		super("YouTube", R.drawable.player_youtube);
		setShortCuts();
	}
	
	@Override
	protected void setShortCuts() {
		int[] t1 ={VK_LEFT};
		shortCuts.put(back, t1);
		int[] t2 ={VK_RIGHT};
		shortCuts.put(forward, t2);
		int[] t3 ={VK_SPACE};
		shortCuts.put(play, t3);
		int[] t4 ={VK_SPACE, VK_0};
		shortCuts.put(stop, t4);
		int[] t5 ={VK_9};
		shortCuts.put(fastFor, t5);
		int[] t6 ={VK_0};
		shortCuts.put(fastBack, t6);
		int[] t7 ={VK_9};
		shortCuts.put(next, t7);
		int[] t8 ={VK_0};
		shortCuts.put(prev, t8);
		int[] t9 ={VK_DOWN};
		shortCuts.put(minus, t9);
		int[] t10 ={VK_UP};
		shortCuts.put(plus, t10);
		int[] t11 ={VK_F};
		shortCuts.put(full, t11);
		int[] t12 ={VK_DOWN};
		shortCuts.put(mute, t12);
	}
}
