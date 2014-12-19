package engineering_thesis_project.android.network.manager;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class Receiver extends AsyncTask<Void, Void, byte[]> {

	public static ImageView video;
	public static Socket socket;
	//long start;
	public Receiver() {
		//start = System.currentTimeMillis();
	}

	@Override
	protected byte[] doInBackground(Void... params) {
		Log.v("Reciver doInBackgroud","25 linia");
		int len;
		byte[] data = null;
		try {
			
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			len = dis.readInt();
			data = new byte[len];
			if (len > 0) {
				dis.readFully(data, 0, len);
			}
			//dis.close();
			
		} catch (IOException e) {
			Log.e(" Exception Reciver doInBackgroud","41 linia");
		}
		Log.v("Reciver doInBackgroud","43 linia");
		//long stop = System.currentTimeMillis();
		//long d = stop - start;
		//Log.v("czas zmierzony","czas recivera "+d+"ms = "+d/1000+"s");
		return data;
	}

	@Override
	protected void onPostExecute(byte[] result) {
		super.onPostExecute(result);
		Log.v("Reciver post","46 linia");
		if(result == null){
			Log.e("Reciver post result jest nullem","51 linia");
			return;
		}
		video.setImageBitmap(BitmapFactory.decodeByteArray(result, 0,
				result.length));
	}

}
