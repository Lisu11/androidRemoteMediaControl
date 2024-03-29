package engineering_thesis_project.android.statics;

/**
 * Class with constants used in application
 * @author lisu
 */
public class Constants {

	/**-----  Controller number -----*/
	public final static int MOUSE = 1;
	public final static int KEYBOARD = 2;
	public final static int STREAM =3;
	public final static int REMOTE =4;

	/**------ special tags you can use wherever you want ---*/
	public final static String TOUCHPAD_TAG = "TOUCHPAD_TAG";
	public final static String KEYBOARD_TAG = "KEYBOARD_TAG";
	public final static String STREAM_TAG = "STREAM_TAG";
	public final static String REMOTE_TAG = "REMOTE_TAG";

	/**------ tag for bundle is a key to a value 
	  ------ with previous used view */
	public final static String PREV_VIEW = "PREV_VIEW";

	/**
	 * Constants used to determine which connection is established  
	 */
	public final static String CONNECTION_TYPE ="CONNECTION_TYPE";
	public final static int BLUETOOTH =0;
	public final static int WIFI =1;

	/**
	 * JSON Object constants
	 */
	public final static String JSON_FILE = "settings.json";
	public final static String JSON_MOUSE_SENSITIVITY = "MOUSE_SENSITIVITY";
	public final static String JSON_MOUSE_ENABLE_TOUCH = "MOUSE_CLICK_ON_TOUCH";
	public final static String JSON_MOUSE_ENABLE_SCROLL = "MOUSE_SCROLL";
	public final static String JSON_STREAM_FRAMERATE = "STREAM_FRAMERATE";
	public final static String JSON_STREAM_ENABLE_TOUCH = "STREAM_TOUCH";

	/**
	 * Constants of remote controllers
	 */
	public final static int REMOTE_VLC = 1;
	public final static int REMOTE_WMP = 2;
	public final static int REMOTE_GOM =3;
	public final static int REMOTE_YOUTUBE =4;
	public final static int REMOTE_WINAMP =5;
	
}
