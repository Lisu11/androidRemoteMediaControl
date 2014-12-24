package engineering_thesis_project.android.controlers.mouse;

/**
 * Interface for listening Touchpad events
 * @author lisu
 */
public interface OnTouchpadEventListener {

	/**
	 * Method called when touchpad is being swiped
	 * Method is called multiple times during one swipe gesture
	 * To be specific every time when position has changed 
	 * @param x first coordinate of translation vector
	 * @param y second coordinate of translation vector
	 * @return true if event was consumed false otherwise
	 */
	public boolean onSwipe(double x, double y);

	/**
	 * Method called when touchpad is being clicked
	 * @param x first coordinate of clicked point
	 * @param y second coordinate of clicked point
	 * @return true if event was consumed false otherwise
	 */
	public boolean onTouchpadClicked(double x, double y);

	/**
	 * Method called when right button is being pressed
	 * @return true if event was consumed false otherwise
	 */
	public boolean onRightButtonPressed();

	/**
	 * Method called when right button is being released
	 * @return true if event was consumed false otherwise
	 */
	public boolean onRightButtonReleased();

	/**
	 * Method called when left button is being pressed
	 * @return true if event was consumed false otherwise
	 */
	public boolean onLeftButtonPressed();

	/**
	 * Method called when left button is being pressed
	 * @return true if event was consumed false otherwise
	 */
	public boolean onLeftButtonReleased();

	/**
	 * Method called when mouse wheel or 
	 * touchpad scroll area is being swiped
	 * @param progress number of wheel notches 
	 * @return true if event was consumed false otherwise
	 */
	public boolean onWheelMoved(int progress);
}
