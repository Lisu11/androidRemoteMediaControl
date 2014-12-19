package engineering_thesis_project.android.controlers.mouse;

public interface OnTouchpadEventListener {
	public boolean onSwipe(double x, double y);
	public boolean onTouchpadClicked(double x, double y);
	public boolean onRightButtonPressed();
	public boolean onRightButtonReleased();
	public boolean onLeftButtonPressed();
	public boolean onLeftButtonReleased();
	public boolean onWheelMoved(int progress);
}
