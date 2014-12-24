package engineering_thesis_project.android.controlers.keyboard;

/**
 * Interface for listening keyboards events.
 * extends AWTConstants cause arguments for some methods 
 * needs to know which keycode is for which key
 * @author lisu
 */
public interface OnKeyboardEventListener extends AWTConstants{

	/**
	 * Is called when some non-alphanumeric
	 * button was pressed. 
	 * @param button keycode of pressed button
	 * @return true if event was consumed false otherwise
	 */
	public boolean onButtonPressed(int button);

	/**
	 * Method is called when some alphanumeric
	 * button was pressed
	 * @param button character of pressed button
	 * @return true if event was consumed false otherwise
	 */
	public boolean onCharButtonPressed(char button);

	/**
	 * Method is called when some key combination
	 * with alphanumeric button was pressed
	 * @param button character of pressed button
	 * @param combination keycodes array of pressed buttons 
	 * @return true if event was consumed false otherwise
	 */
	public boolean onCharButtonPressed(char button, int[] combination);

	/**
	 * Method is called when some key combination
	 * without alphanumeric button was pressed
	 * @param keys
	 * @return true if event was consumed false otherwise
	 */
	public boolean onKeyCombinationPressed(int[] keys);
}
