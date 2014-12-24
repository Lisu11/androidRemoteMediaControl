package engineering_thesis_project.android.network.protocol;

/**
 * 
 * @author lisu
 *
 */
public class KeyboardProtocol extends Protocol{
	
	public final static byte MODE_TYPE_MASK = (byte)0x80;
	public final static byte SINGLE_KEY_MODE = (byte)0x00;
	public final static byte MULTIPLE_KEY_MODE = (byte)0x80;

	/**
	 * Puts mask on frame and informs if frame is in single key mode
	 * 
	 * @param frame
	 * @return true if frame is in two int mode false otherwise
	 */
	public static boolean isSingleKeyMode(byte frame){
		return (byte)(frame & MODE_TYPE_MASK) == SINGLE_KEY_MODE;
	}

	/**
	 * Puts mask on frame and informs if frame is multiple keys mode
	 * 
	 * @param frame
	 * @return true if frame is in two int mode false otherwise
	 */
	public static boolean isMultipleKeyMode(byte frame){
		return !isSingleKeyMode(frame);
	}
}
