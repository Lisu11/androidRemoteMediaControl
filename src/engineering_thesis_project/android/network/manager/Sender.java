package engineering_thesis_project.android.network.manager;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
import engineering_thesis_project.android.ui.activities.MainActivity;

public class Sender extends AsyncTask<Void, Void, Boolean> {

	private boolean protocolFlag = false;
	private byte protocol;
	private boolean coordFlag = false;
	private boolean progressFlag = false;
	private int x;
	private int y;
	private boolean listFlag = false;
	private int[] list;
	private static MainActivity context;
 
    public Sender(byte protocol) {
    	this.protocol = protocol;
    	protocolFlag = true;
    }

    public Sender(int[] list) {
    	listFlag = true;
    	this.list = list;
    }
    
    public Sender(byte protocol, int x, int y) {
    	this.protocol = protocol;
    	protocolFlag = true;
    	coordFlag = true;
    	this.x = x;
    	this.y = y;
    }

    public Sender(byte protocol, int progress) {
    	this.protocol = protocol;
    	protocolFlag = true;
    	progressFlag = true;
    	this.x = progress;
    }
    
    @Override
    protected void onPreExecute() {
    	super.onPreExecute();
    }

	@Override
	protected Boolean doInBackground(Void... params) {
		Log.i("sender do in background", "send attempt");
		try {
			
			if(protocolFlag){
				NetworkManager.getInstance().getOut().writeObject(protocol);
				Log.v("Message sended", protocol+"");
			}
			if(progressFlag){
				NetworkManager.getInstance().getOut().writeObject(x);
				Log.v("progress sended", x+"");
			}
			if(coordFlag){
				NetworkManager.getInstance().getOut().writeObject(x);
				NetworkManager.getInstance().getOut().writeObject(y);
			} else if(listFlag) {
				if(list.length > 1){
					NetworkManager.getInstance().getOut().writeObject(list.length);
				}
				for(int item : list) {
					NetworkManager.getInstance().getOut().writeObject(item);
				}
			}
			return true;
		} catch (IOException e) {
			Log.e("Message cannot be send", protocol+"");
			return false;
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(!result){
			if(context == null){
				Log.e("Context in sender is null", "");
			} else {
				context.askForRestart();
			}
		}
	}

	public static void setContext(MainActivity context) {
		Sender.context = context;
	}
}
