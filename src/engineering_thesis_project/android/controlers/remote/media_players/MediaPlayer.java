package engineering_thesis_project.android.controlers.remote.media_players;

import java.util.HashMap;

import engineering_thesis_project.android.controlers.keyboard.AWTConstants;
import engineering_thesis_project.android.controlers.keyboard.Keyboard;

public abstract class MediaPlayer implements AWTConstants{

	/**
	 * Media player name
	 */
	public final String playerName;
	/**
	 * Media player logo id in drawable
	 */
	public final int playerIcon;

	/**
	 * maps button with keycodes sequence
	 */
	protected HashMap<String, int[]> shortCuts; 

	/** hashmap key corresponding to stop button */
	protected final String stop = "stop";
	/** hashmap key corresponding to back button */
	protected final String back = "back";
	/** hashmap key corresponding to forward button */
	protected final String forward = "forward";
	/** hashmap key corresponding to play button */
	protected final String play = "playPouse";
	/** hashmap key corresponding to fastFor button */
	protected final String fastFor = "fastFor";
	/** hashmap key corresponding to fastBack button */
	protected final String fastBack = "fastBack";
	/** hashmap key corresponding to next button */
	protected final String next = "next";
	/** hashmap key corresponding to prev button */
	protected final String prev = "prev";
	/** hashmap key corresponding to minus button */
	protected final String minus = "minus";
	/** hashmap key corresponding to plus button */
	protected final String plus = "plus";
	/** hashmap key corresponding to full button */
	protected final String full = "full";
	/** hashmap key corresponding to mute button */
	protected final String mute = "mute";

	/**
	 * You should use this constructor in inherited class
	 * @param name name of media player
	 * @param iconID logo if media player
	 */
	protected MediaPlayer(String name, int iconID){
		shortCuts = new HashMap<String, int[]>();
		playerIcon = iconID;
		playerName = name;
	}

	/**
	 * This method must be implemented in this way:
	 * take every protected final String key put into hashmap
	 * with keycodes you want to click by pressing this button
	 */
	protected abstract void setShortCuts(); 

	/**
	 * Method calls when stop button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */
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
	
	/**
	 * Method calls when back button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void backClick(Keyboard keyboard) throws WrongImplementationException{
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
	
	/**
	 * Method calls when for button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void forClick(Keyboard keyboard) throws WrongImplementationException{
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
	
	/**
	 * Method calls when fastBack button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void fastBackClick(Keyboard keyboard)  throws WrongImplementationException{
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
	
	/**
	 * Method calls when fastFor button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void fastForClick(Keyboard keyboard)  throws WrongImplementationException{
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

	/**
	 * Method calls when next button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void nextClick(Keyboard keyboard) throws WrongImplementationException{
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

	/**
	 * Method calls when prev button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void prevClick(Keyboard keyboard) throws WrongImplementationException{
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
	
	/**
	 * Method calls when minus button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void minusClick(Keyboard keyboard) throws WrongImplementationException{
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
	
	/**
	 * Method calls when plus button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void plusClick(Keyboard keyboard) throws WrongImplementationException{
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

	/**
	 * Method calls when full button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void fullClick(Keyboard keyboard) throws WrongImplementationException{
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
	
	/**
	 * Method calls when mute button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void muteClick(Keyboard keyboard) throws WrongImplementationException{
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

	/**
	 * Method calls when playPouse button was clicked
	 * sends set keys sequences to keyboard
	 * @param keyboard keyboard object to which you want to send buttons sequence
	 * @throws WrongImplementationException when you forgot add key to hashmap
	 */public final void playPouseClick(Keyboard keyboard) throws WrongImplementationException{
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
