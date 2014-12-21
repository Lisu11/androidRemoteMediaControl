package engineering_thesis_project.android.controlers.remote.media_players;

import engineering_thesis_project.android.ui.activities.R;

public class FoobarPlayer extends MediaPlayer {

	public FoobarPlayer(){
		super("Foobar", R.drawable.player_foobar);
		setShortCuts();
	}
	
	@Override
	protected void setShortCuts() {
		int[] t1 ={};
		shortCuts.put(back, t1);
		int[] t2 ={};
		shortCuts.put(forward, t2);
		int[] t3 ={};
		shortCuts.put(play, t3);
		int[] t4 ={};
		shortCuts.put(stop, t4);
		int[] t5 ={};
		shortCuts.put(fastFor, t5);
		int[] t6 ={};
		shortCuts.put(fastBack, t6);
		int[] t7 ={};
		shortCuts.put(next, t7);
		int[] t8 ={};
		shortCuts.put(prev, t8);
		int[] t9 ={};
		shortCuts.put(minus, t9);
		int[] t10 ={};
		shortCuts.put(plus, t10);
		int[] t11 ={};
		shortCuts.put(full, t11);
		int[] t12 ={};
		shortCuts.put(mute, t12);
	}

}
