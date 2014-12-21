package engineering_thesis_project.android.controlers.remote.media_players;

public class WrongImplementationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String mMessage = "";

	public WrongImplementationException() { }
	
	public WrongImplementationException(String message){
		this.mMessage = message;
	}

	public String getMessage(){
		return mMessage;
	}

}
