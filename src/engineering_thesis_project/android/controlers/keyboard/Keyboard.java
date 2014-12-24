package engineering_thesis_project.android.controlers.keyboard;

import java.io.IOException;

import android.util.Log;
import engineering_thesis_project.android.network.manager.ConnectionManager;
import engineering_thesis_project.android.network.protocol.KeyboardProtocol;
import engineering_thesis_project.android.network.protocol.Protocol;

/**
 * Class for managing every action that can come from 
 * android keyboard or any other keyboard.
 * Class is responsible for interpret and transfer data 
 * from UI to networkManager. 
 * @author lisu
 *
 */
public class Keyboard implements OnKeyboardEventListener, AWTConstants {

	@Override
	/**
	 * Method from OnKeyboardEventListener. 
	 * Creates special protocol message for pressed button
	 * and passes to ConnectionManager 
	 * @param button keycode of pressed button
	 * @return true if event was consumed successfully false otherwise
	 */
	public boolean onButtonPressed(int button) {
		try {
			ConnectionManager.instance
					.sendFrame(
							(byte) (Protocol.KEYBOARD | KeyboardProtocol.SINGLE_KEY_MODE));
			int[] array = new int[1];
			array[0] =  button;
			ConnectionManager.instance
					.sendFrames(array);
		} catch (IOException e) {
			Log.e("keyboard button pressed exception", e.getMessage());
			return false;
		}
		Log.v("onButtonPressed", "bttonId= " +button);
		return true;
	}

	@Override
	/**
	 * Method from OnKeyboardEventListener. 
	 * Creates special protocol message for pressed combination of buttons
	 * and passes to ConnectionManager 
	 * @param keys keycodes array of pressed buttons
	 * @return true if event was consumed successfully false otherwise
	 */
	public boolean onKeyCombinationPressed(int[] keys) {
		try {
			ConnectionManager.instance
					.sendFrame(
							(byte) (Protocol.KEYBOARD | KeyboardProtocol.MULTIPLE_KEY_MODE));
			ConnectionManager.instance.sendFrames(keys);
		} catch (IOException e) {
			Log.e("keyboard button sequence pressed exception", e.getMessage());
			return false;
		}
		Log.v("onButtonPressed", "bttonsArray= " +keys.toString());
		return true;
	}

	@Override
	/**
	 * Method from OnKeyboardEventListener. 
	 * Creates special protocol message for pressed button
	 * and passes to ConnectionManager 
	 * @param button character of pressed button
	 * @return true if event was consumed successfully false otherwise
	 */
	public boolean onCharButtonPressed(char button) {
		try{
			keyIdByChar(button, null);
		}catch(IllegalArgumentException e){
			Log.v("Illegeal argument",""+ e.getMessage());
			return false;
		}
		return false;
	}

	/**
	 * Method is finding keyCode for c character 
	 * then merging with array and passing to onKeyCombinationPressed method
	 * @param c ascii character for change
	 * @param array of keycodes can be null
	 * @throws IllegalArgumentException When character c is not supported
	 */
	protected void keyIdByChar(char c, int[] array) throws IllegalArgumentException{
		switch (c) {
	        case 'a': reciveKeys(array, VK_A); break;
	        case 'b': reciveKeys(array, VK_B); break;
	        case 'c': reciveKeys(array, VK_C); break;
	        case 'd': reciveKeys(array, VK_D); break;
	        case 'e': reciveKeys(array, VK_E); break;
	        case 'f': reciveKeys(array, VK_F); break;
	        case 'g': reciveKeys(array, VK_G); break;
	        case 'h': reciveKeys(array, VK_H); break;
	        case 'i': reciveKeys(array, VK_I); break;
	        case 'j': reciveKeys(array, VK_J); break;
	        case 'k': reciveKeys(array, VK_K); break;
	        case 'l': reciveKeys(array, VK_L); break;
	        case 'm': reciveKeys(array, VK_M); break;
	        case 'n': reciveKeys(array, VK_N); break;
	        case 'o': reciveKeys(array, VK_O); break;
	        case 'p': reciveKeys(array, VK_P); break;
	        case 'q': reciveKeys(array, VK_Q); break;
	        case 'r': reciveKeys(array, VK_R); break;
	        case 's': reciveKeys(array, VK_S); break;
	        case 't': reciveKeys(array, VK_T); break;
	        case 'u': reciveKeys(array, VK_U); break;
	        case 'v': reciveKeys(array, VK_V); break;
	        case 'w': reciveKeys(array, VK_W); break;
	        case 'x': reciveKeys(array, VK_X); break;
	        case 'y': reciveKeys(array, VK_Y); break;
	        case 'z': reciveKeys(array, VK_Z); break;
	        case 'A': reciveKeys(array, VK_SHIFT, VK_A); break;
	        case 'B': reciveKeys(array, VK_SHIFT, VK_B); break;
	        case 'C': reciveKeys(array, VK_SHIFT, VK_C); break;
	        case 'D': reciveKeys(array, VK_SHIFT, VK_D); break;
	        case 'E': reciveKeys(array, VK_SHIFT, VK_E); break;
	        case 'F': reciveKeys(array, VK_SHIFT, VK_F); break;
	        case 'G': reciveKeys(array, VK_SHIFT, VK_G); break;
	        case 'H': reciveKeys(array, VK_SHIFT, VK_H); break;
	        case 'I': reciveKeys(array, VK_SHIFT, VK_I); break;
	        case 'J': reciveKeys(array, VK_SHIFT, VK_J); break;
	        case 'K': reciveKeys(array, VK_SHIFT, VK_K); break;
	        case 'L': reciveKeys(array, VK_SHIFT, VK_L); break;
	        case 'M': reciveKeys(array, VK_SHIFT, VK_M); break;
	        case 'N': reciveKeys(array, VK_SHIFT, VK_N); break;
	        case 'O': reciveKeys(array, VK_SHIFT, VK_O); break;
	        case 'P': reciveKeys(array, VK_SHIFT, VK_P); break;
	        case 'Q': reciveKeys(array, VK_SHIFT, VK_Q); break;
	        case 'R': reciveKeys(array, VK_SHIFT, VK_R); break;
	        case 'S': reciveKeys(array, VK_SHIFT, VK_S); break;
	        case 'T': reciveKeys(array, VK_SHIFT, VK_T); break;
	        case 'U': reciveKeys(array, VK_SHIFT, VK_U); break;
	        case 'V': reciveKeys(array, VK_SHIFT, VK_V); break;
	        case 'W': reciveKeys(array, VK_SHIFT, VK_W); break;
	        case 'X': reciveKeys(array, VK_SHIFT, VK_X); break;
	        case 'Y': reciveKeys(array, VK_SHIFT, VK_Y); break;
	        case 'Z': reciveKeys(array, VK_SHIFT, VK_Z); break;
	        case '`': reciveKeys(array, VK_BACK_QUOTE); break;
	        case '\b': reciveKeys(array, VK_BACK_SPACE); break;
	        case '0': reciveKeys(array, VK_0); break;
	        case '1': reciveKeys(array, VK_1); break;
	        case '2': reciveKeys(array, VK_2); break;
	        case '3': reciveKeys(array, VK_3); break;
	        case '4': reciveKeys(array, VK_4); break;
	        case '5': reciveKeys(array, VK_5); break;
	        case '6': reciveKeys(array, VK_6); break;
	        case '7': reciveKeys(array, VK_7); break;
	        case '8': reciveKeys(array, VK_8); break;
	        case '9': reciveKeys(array, VK_9); break;
	        case '-': reciveKeys(array, VK_MINUS); break;
	        case '=': reciveKeys(array, VK_EQUALS); break;
	        case '~': reciveKeys(array, VK_SHIFT, VK_BACK_QUOTE); break;
	        case '!': reciveKeys(array, VK_EXCLAMATION_MARK); break;
	        case '@': reciveKeys(array, VK_AT); break;
	        case '#': reciveKeys(array, VK_NUMBER_SIGN); break;
	        case '$': reciveKeys(array, VK_DOLLAR); break;
	        case '%': reciveKeys(array, VK_SHIFT, VK_5); break;
	        case '^': reciveKeys(array, VK_CIRCUMFLEX); break;
	        case '&': reciveKeys(array, VK_AMPERSAND); break;
	        case '*': reciveKeys(array, VK_ASTERISK); break;
	        case '(': reciveKeys(array, VK_LEFT_PARENTHESIS); break;
	        case ')': reciveKeys(array, VK_RIGHT_PARENTHESIS); break;
	        case '_': reciveKeys(array, VK_UNDERSCORE); break;
	        case '+': reciveKeys(array, VK_PLUS); break;
	        case '\t': reciveKeys(array, VK_TAB); break;
	        case '\n': reciveKeys(array, VK_ENTER); break;
	        case '[': reciveKeys(array, VK_OPEN_BRACKET); break;
	        case ']': reciveKeys(array, VK_CLOSE_BRACKET); break;
	        case '\\': reciveKeys(array, VK_BACK_SLASH); break;
	        case '{': reciveKeys(array, VK_SHIFT, VK_OPEN_BRACKET); break;
	        case '}': reciveKeys(array, VK_SHIFT, VK_CLOSE_BRACKET); break;
	        case '|': reciveKeys(array, VK_SHIFT, VK_BACK_SLASH); break;
	        case ';': reciveKeys(array, VK_SEMICOLON); break;
	        case ':': reciveKeys(array, VK_COLON); break;
	        case '\'': reciveKeys(array, VK_QUOTE); break;
	        case '"': reciveKeys(array, VK_QUOTEDBL); break;
	        case ',': reciveKeys(array, VK_COMMA); break;
	        case '<': reciveKeys(array, VK_SHIFT, VK_COMMA); break;
	        case '.': reciveKeys(array, VK_PERIOD); break;
	        case '>': reciveKeys(array, VK_SHIFT, VK_PERIOD); break;
	        case '/': reciveKeys(array, VK_SLASH); break;
	        case '?': reciveKeys(array, VK_SHIFT, VK_SLASH); break;
	        case ' ': reciveKeys(array, VK_SPACE); break;
	        default:
	            throw new IllegalArgumentException("Cannot type character " + c);
		}
	    
	}

	/**
	 * Method is merging two arrays of keycodes 
	 * and passing to onKeyCombinationPressed Method
	 * @param array first array
	 * @param keyCodes second array
	 */
	private void reciveKeys(int[] array, int... keyCodes) {
		if(keyCodes.length == 1 && (array == null || array.length == 0)){
			onButtonPressed(keyCodes[0]);
		} else {
			int[] combination = new int[array.length+keyCodes.length];
			int i=0, j=0;
			while(i < array.length){
				combination[i] = array[i++];
			}

			while(i < combination.length && j < keyCodes.length){
				combination[i++] = keyCodes[j++];
			}

			onKeyCombinationPressed(combination);
		}
	}

	@Override
	/**
	 * Method from OnKeyboardEventListener. 
	 * Creates special protocol message for pressed combination of buttons
	 * and passes to ConnectionManager 
	 * @param button character of pressed button
	 * @param combination keycodes array of pressed buttons
	 * @return true if event was consumed successfully false otherwise
	 */
	public boolean onCharButtonPressed(char button, int[] combination) {
		try{
			keyIdByChar(button, combination);
		}catch(IllegalArgumentException e){
			Log.e("keyboard button sequence pressed exception", e.getMessage());
			return false;
		}
		return true;
	}
}
