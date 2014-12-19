package engineering_thesis_project.android.network.protocol;

public class StreamProtocol extends Protocol {
	public final static byte START_STREAM = (byte)0x00;
	public final static byte STOP_STREAM = (byte)0x10;
	
	public static boolean isStartStream(byte frame){
		return (byte)(frame & MODE_MASK) == START_STREAM;
	}

	public static boolean isStopStream(byte frame){
		return !isStartStream(frame);
	}
}
