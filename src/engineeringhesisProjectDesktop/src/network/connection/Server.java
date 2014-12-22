package network.connection;
import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {

	private ServerSocket serversocket;
	private Socket socket;
	private ObjectInputStream in;
	private OutputStream out;
	ProtocolHandler handler;
	
	public Server(int port) throws IOException, AWTException {
		serversocket = new ServerSocket();
		serversocket.setReuseAddress(true);
		serversocket.bind(new InetSocketAddress(port));
		System.out.println("socket utworzony");
	}

	private void InOutSetup() throws IOException, AWTException {
		out = socket.getOutputStream();
		System.out.println("out poszed\n");

		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("in poszed\n");
		handler = new ProtocolHandler(in, out);
	}

	@SuppressWarnings("deprecation")
	public void finish(){
		if(serversocket == null)
			return;
		try {
			serversocket.close();
			in.close();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		this.stop();
	}
	
	@Override
	public void run() {
		try {
			System.out.println("czekamy na polaczenie");
			socket = serversocket.accept();
			System.out.println("server stoi i polaczony");
			try{
				InOutSetup();
				System.out.println("strumienie zainicjowane");
			}catch(IOException e){
				System.out.println("strumienie niezainicjowane\n"+e.getLocalizedMessage());
				//TODO tu juz napweno okienko z errorem
				return;
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (true) {
				try {
					System.out.println("Czekam na wiadomosc");
					byte frame =  (byte) in.readObject();
					System.out.println("wiadomosc "+frame);
					handler.handleFrame(frame);
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.out.println("koniec");
					finish();
					break;
				}
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			e1.printStackTrace();
			System.out.println("koniec");
			finish();
		}
	}

	
}
