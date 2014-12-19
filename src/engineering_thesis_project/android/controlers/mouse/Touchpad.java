package engineering_thesis_project.android.controlers.mouse;

import java.io.IOException;

import android.util.Log;
import engineering_thesis_project.android.network.manager.NetworkManager;
import engineering_thesis_project.android.network.protocol.MouseProtocol;
import engineering_thesis_project.android.network.protocol.Protocol;

public class Touchpad implements OnTouchpadEventListener {

	private static double sensitivity;
	private static boolean scrollEnaled;
	
	public static void setSensitivity(double sensitivity) {
		Touchpad.sensitivity = sensitivity;
	}
	
	public static double getSensitivity() {
		return sensitivity;
	}

	
	public static boolean isScrollEnaled() {
		return scrollEnaled;
	}

	public static void setScrollEnaled(boolean scrollEnaled) {
		Touchpad.scrollEnaled = scrollEnaled;
	}
	
	public Touchpad(double sensitivity) {
		Touchpad.sensitivity = sensitivity;
		scrollEnaled = false;
	}
	
	public Touchpad(double sensitivity, boolean enableScroll){
		Touchpad.sensitivity = sensitivity;
		scrollEnaled = enableScroll;
	}

	@Override
	public boolean onSwipe(double x, double y) {
		try {
			NetworkManager
					.getInstance()
					.sendFrames(
							(byte) (Protocol.MOUSE | MouseProtocol.POINTER_TRANSLATION),
							(int) (x*sensitivity), (int) (y*sensitivity));
		} catch (IOException e) {
			Log.e("right reeased exception", e.getMessage());
		}
		Log.v("on swipe", "x=" + x + " y=" + y);
		return true;
	}

	@Override
	public boolean onTouchpadClicked(double x, double y) {
		try {
			NetworkManager.getInstance().sendFrames(
					(byte) (Protocol.MOUSE | MouseProtocol.POINTER_SETUP),
					(int) x, (int) y);
		} catch (IOException e) {
			Log.e("right reeased exception", e.getMessage());
		}
		Log.v("on touchpad clicked", "x=" + x + " y=" + y);
		return true;
	}

	@Override
	public boolean onRightButtonPressed() {
		try {
			NetworkManager.getInstance().sendFrame(
					(byte) (Protocol.MOUSE | MouseProtocol.RIGHT_PRESSED));
		} catch (IOException e) {
			Log.e("right pressed exception", e.getMessage());
		}
		Log.v("on right pressed", " RIGHT");
		return true;
	}

	@Override
	public boolean onRightButtonReleased() {
		try {
			NetworkManager.getInstance().sendFrame(
					(byte) (Protocol.MOUSE | MouseProtocol.RIGHT_RELEASED));
		} catch (IOException e) {
			Log.e("right reeased exception", e.getMessage());
		}
		Log.v("on right released", " RIGHT");
		return true;
	}

	@Override
	public boolean onLeftButtonPressed() {
		try {
			NetworkManager.getInstance().sendFrame(
					(byte) (Protocol.MOUSE | MouseProtocol.LEFT_PRESSED));
		} catch (IOException e) {
			Log.e("left pressed exception", e.getMessage());
		}
		Log.v("on left pressed", " LEFT");
		return true;
	}

	@Override
	public boolean onLeftButtonReleased() {
		try {
			NetworkManager.getInstance().sendFrame(
					(byte) (Protocol.MOUSE | MouseProtocol.LEFT_RELEASED));
		} catch (IOException e) {
			Log.e("left reeased exception", e.getMessage());
		}
		Log.v("on left released", " LEFT");
		return true;
	}

	@Override
	public boolean onWheelMoved(int progress) {
		if(MyGestureDetector.isSwipeOnEdgeEnabled()){
			try {
				NetworkManager.getInstance().sendFrames(
						(byte) (Protocol.MOUSE | MouseProtocol.WHEEL_MOVED), progress);
			} catch (IOException e) {
				Log.e("wheel moved exception", e.getMessage());
			}
		}
		Log.v("on wheel moved", " progress="+progress);
		return scrollEnaled;
	}

}
