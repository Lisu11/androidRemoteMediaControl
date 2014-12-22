package network.connection;
import java.awt.AWTException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import controllers.Streamer;


public class StreamSender implements Runnable{
	int frameRate;
	volatile boolean stopThread = false; 
	private OutputStream out;
	private static Streamer streamer = new Streamer();
	long start;
	public StreamSender(OutputStream out, int frameRate) throws AWTException{	
		start = System.currentTimeMillis();
		this.frameRate = frameRate;
		this.out = out;
	}

	public synchronized void stopThread(){
		stopThread = true;
	}
	
	@Override
	public void run() {
		byte[] image;
		DataOutputStream dos = new DataOutputStream(out);
		
			image = streamer.getScreenShot();//TODO robot moze byc nullem bo jest statyczne a moglo nie powstac
			try {
				System.out.println("probuje wyslac");
				dos.writeInt(image.length);
				out.write(image, 0, image.length);
				System.out.println("wyslalem");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("exception poszedl");
			}
		long end = System.currentTimeMillis();
		long d = end - start;
		System.out.println(" czas sendera "+d+"ms = "+d/1000+"s");
	}
	
}
