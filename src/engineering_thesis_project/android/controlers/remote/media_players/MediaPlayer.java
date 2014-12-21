package engineering_thesis_project.android.controlers.remote.media_players;

import java.util.HashMap;

import engineering_thesis_project.android.controlers.keyboard.AWTConstants;
import engineering_thesis_project.android.controlers.keyboard.Keyboard;

public abstract class MediaPlayer implements AWTConstants{

	public final String playerName;

	public final int playerIcon;

	protected HashMap<String, int[]> shortCuts; 
	
	protected final String stop = "stop";
	protected final String back = "back";
	protected final String forward = "forward";
	protected final String play = "playPouse";
	protected final String fastFor = "fastFor";
	protected final String fastBack = "fastBack";
	protected final String next = "next";
	protected final String prev = "prev";
	protected final String minus = "minus";
	protected final String plus = "plus";
	protected final String full = "full";
	protected final String mute = "mute";
	
	protected MediaPlayer(String name, int iconID){
		shortCuts = new HashMap<String, int[]>();
		playerIcon = iconID;
		playerName = name;
	}

	protected abstract void setShortCuts(); 
	
	public final void stopClick(Keyboard keyboard) throws WrongImplementationException{
		String b = stop;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+ b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void backClick(Keyboard keyboard) throws WrongImplementationException{
		String b = back;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+ b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void forClick(Keyboard keyboard) throws WrongImplementationException{
		String b = forward;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void fastBackClick(Keyboard keyboard)  throws WrongImplementationException{
		String b = fastBack;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void fastForClick(Keyboard keyboard)  throws WrongImplementationException{
		String b = fastFor;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}

	public final void nextClick(Keyboard keyboard) throws WrongImplementationException{
		String b = next;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}

	public final void prevClick(Keyboard keyboard) throws WrongImplementationException{
		String b = prev;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void minusClick(Keyboard keyboard) throws WrongImplementationException{
		String b = minus;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void plusClick(Keyboard keyboard) throws WrongImplementationException{
		String b = plus;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}

	public final void fullClick(Keyboard keyboard) throws WrongImplementationException{
		String b = full;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}
	
	public final void muteClick(Keyboard keyboard) throws WrongImplementationException{
		String b = mute;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}

	public final void playPouseClick(Keyboard keyboard) throws WrongImplementationException{
		String b = play;
		int[] keys = shortCuts.get(b);
		if(keys == null){
			throw new WrongImplementationException("Maybe You forgot implement shortcut for key "+b);
		} else if(keys.length == 1){
			keyboard.onButtonPressed(keys[0]);
		} else {
			keyboard.onKeyCombinationPressed(keys);
		}
	}

}
