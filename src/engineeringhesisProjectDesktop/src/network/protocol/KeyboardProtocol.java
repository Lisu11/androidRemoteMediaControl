package network.protocol;


public class KeyboardProtocol extends Protocol{
	
	public final static byte MODE_TYPE_MASK = (byte)0x80;
	public final static byte SINGLE_KEY_MODE = (byte)0x00;
	public final static byte MULTIPLE_KEY_MODE = (byte)0x80;


	public static boolean isSingleKeyMode(byte frame){
		return (byte)(frame & MODE_TYPE_MASK) == SINGLE_KEY_MODE;
	}

	public static boolean isMultipleKeyMode(byte frame){
		return !isSingleKeyMode(frame);
	}
}
