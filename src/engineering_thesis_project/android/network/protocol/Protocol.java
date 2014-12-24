package engineering_thesis_project.android.network.protocol;


/**
 * 
 * @author lisu
 *
 */
public class Protocol {
	public final static byte CONTROLLER_MASK = (byte)0x0F;
	public final static byte MODE_MASK = (byte)0xF0;

	//Maksymalna ilosc controlerow to 16 kazdy w 16 trybach
	public final static byte MOUSE = (byte)0x00;
	public final static byte KEYBOARD = (byte)0x01;
	public final static byte STREAM = (byte)0x02;
	public final static byte REMOTE = (byte)0x03;
	public final static byte HANDSHAKE_TO = (byte)0x04;
	public final static byte HANDSHAKE_FROM = (byte)0x14;

	/**
	 * Puts mask on frame. hen inform what controller is coded in frame
	 * 
	 * @param frame
	 * @return controller id
	 */
	public static byte getController(byte frame){
		return (byte)(frame & CONTROLLER_MASK);
	}

	/**
	 * Puts mask on frame. hen inform what mode is coded in frame
	 * 
	 * @param frame
	 * @return mode id
	 */
	public static byte getMode(byte frame){
		return (byte)(frame & MODE_MASK);
	}
}
