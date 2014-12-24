package engineering_thesis_project.android.controlers.remote.media_players;

/**
 * Custom Exception Should be thrown when you implement some abstract method 
 * in wrong way. Where wrong means not right the abstract class demands 
 * and something is missing or something is to much
 * @author lisu
 *
 */
public class WrongImplementationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String mMessage = "";

	/**
	 * Main Constructor if you dont need to pass any message 
	 */
	public WrongImplementationException() { }

	/**
	 * Use this constructor if you want to pass a message by exception 
	 * @param message string you want to pass
	 */
	public WrongImplementationException(String message){
		this.mMessage = message;
	}

	/**
	 * Recover message from exception
	 */
	public String getMessage(){
		return mMessage;
	}

}
