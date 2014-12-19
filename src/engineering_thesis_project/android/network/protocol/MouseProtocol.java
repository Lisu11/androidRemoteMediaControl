package engineering_thesis_project.android.network.protocol;

public class MouseProtocol extends Protocol{
	/*
	 * najpierw bajt potem w zaleznosci od trybu 2 inty lub nic lub 1 int
	 */

	public final static byte MODE_TYPE_MASK = (byte)0x80;
	public final static byte TWO_INT_MODE =  (byte)0x80;
	public final static byte ZERO_INT_MODE = (byte)0x00;
	//tryby bajt + 2 inty
	public final static byte POINTER_TRANSLATION = (byte)0x90;
	public final static byte POINTER_SETUP = (byte)0x80;
	//tryb bajt + 1 int
	public final static byte WHEEL_MOVED = (byte)0x60;
	//tryby bajt + 0 intow
	public final static byte RIGHT_RELEASED = (byte)0x20;
	public final static byte RIGHT_PRESSED = (byte)0x30;
	public final static byte LEFT_PRESSED = (byte)0x40;
	public final static byte LEFT_RELEASED = (byte)0x50;

	public static boolean isTwoIntModeType(byte frame){
		return (byte)(frame & MODE_TYPE_MASK) == TWO_INT_MODE;
	}

	public static boolean isZeroIntModeType(byte frame){
		return !isTwoIntModeType(frame);
	}

	public static boolean isWheelMode(byte frame){
		return (byte)(MODE_MASK & frame) == WHEEL_MOVED;
	}
}
