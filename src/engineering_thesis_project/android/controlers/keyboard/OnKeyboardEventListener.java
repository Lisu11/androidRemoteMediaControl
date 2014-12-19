package engineering_thesis_project.android.controlers.keyboard;


public interface OnKeyboardEventListener {
	public boolean onButtonPressed(int button);
	public boolean onCharButtonPressed(char button);
	public boolean onCharButtonPressed(char button, int[] combination);
	public boolean onKeyCombinationPressed(int[] keys);
}
