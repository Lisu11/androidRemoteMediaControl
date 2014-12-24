package engineering_thesis_project.android.network.protocol;

/**
 * 
 * @author lisu
 * 
 */
public class StreamProtocol extends Protocol {
	public final static byte START_STREAM = (byte) 0x00;
	public final static byte STOP_STREAM = (byte) 0x10;

	/**
	 * Puts mask on frame
	 * 
	 * @param frame
	 * @return true if frame means to start stream false otherwise
	 */
	public static boolean isStartStream(byte frame) {
		return (byte) (frame & MODE_MASK) == START_STREAM;
	}

	/**
	 * Puts mask on frame
	 * 
	 * @param frame
	 * @return true if frame means to stop stream false otherwise
	 */
	public static boolean isStopStream(byte frame) {
		return !isStartStream(frame);
	}
}