package UI;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import network.connection.Server;

public class MainClass extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JRadioButton radioWiFi;
	JRadioButton radioBluetooth;
	JPanel leftPanel;
	JPanel rightPanel;
	JTextArea IPtextField;
	JTextField portTextField;
	JTextField deviceFoundTP;
	JButton connectButton;
	JButton reConnectButton;
	
	Server th;
	
	public MainClass() {
		setTitle("Remote access and control");
		setBounds(200, 200, 500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);

		radioWiFi = new JRadioButton("WiFi");
		radioWiFi.setMnemonic(KeyEvent.VK_W);
		radioWiFi.setSelected(true);
		radioWiFi.addActionListener(this);
		radioBluetooth = new JRadioButton("Bluetooth");
		radioBluetooth.setMnemonic(KeyEvent.VK_B);
		radioBluetooth.addActionListener(this);
		ButtonGroup grup = new ButtonGroup();
		grup.add(radioBluetooth);
		grup.add(radioWiFi);

		leftPanel = new JPanel();
		leftPanel.setBounds(1, 1, 200, 273);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setLayout(new GridLayout(0,1));
		leftPanel.add(radioBluetooth);
		leftPanel.add(radioWiFi);
		leftPanel.add(new JLabel());
		leftPanel.add(new JLabel());

		leftPanel.add(new JLabel());
		connectButton = new JButton("Connect");
		connectButton.addActionListener(this);
		reConnectButton = new JButton("Break connection");
		reConnectButton.addActionListener(this);
		leftPanel.add(reConnectButton);
		reConnectButton.setEnabled(false);
		leftPanel.add(connectButton);
		add(leftPanel);

		rightPanel = new JPanel();
		rightPanel.setBounds(203, 1, 295, 273);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rightPanel.setLayout(new GridLayout(0,2));
		rightPanel.add(new JLabel("System IP"));
		IPtextField = new JTextArea(detectIP());
		IPtextField.setEditable(false);
		//JScrollPane sc = new JScrollPane();
		//sc.add(IPtextField);
		rightPanel.add(IPtextField);

		rightPanel.add(new JLabel("Port"));
		portTextField = new JTextField("1111");
		rightPanel.add(portTextField);
		rightPanel.add(new JLabel("Bluetooth device"));
		deviceFoundTP = new JTextField(); 
		deviceFoundTP.setEditable(false);
		deviceFoundTP.setText(detectBluetoothDev());
		rightPanel.add(deviceFoundTP);
		rightPanel.add(new JLabel("Operating system"));
		rightPanel.add(new JLabel(detectOS()));
		add(rightPanel);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainClass();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(radioWiFi)){
			deviceFoundTP.setEnabled(false);
			portTextField.setEditable(true);
		} else if(arg0.getSource().equals(radioBluetooth)){
			portTextField.setText("1111");
			portTextField.setEditable(false);
			deviceFoundTP.setEnabled(true);
		} else if(arg0.getSource().equals(connectButton)){
			if(radioWiFi.isSelected()){
				connectViaWiFi();
			} else {
				connectViaBluetooth();
			}
		} else if(arg0.getSource().equals(reConnectButton)){
			reConnectButton.setEnabled(false);
			connectButton.setEnabled(true);
			th.finish();
		}
	}

	private void connectViaBluetooth() {
		// TODO Auto-generated method stub
		
	}

	private void connectViaWiFi() {
		// TODO Auto-generated method stub
		int port;
		try{
			port = Integer.parseInt(portTextField.getText());
		}catch (NumberFormatException e){
			//TODO wyswietl komunikat
			return;
		}
		if(portIsValid(port)){
			
			try {
				th = new Server(port);
				th.start();
			} catch (IOException e) {
				// TODO cos trzeba zrobic
				e.printStackTrace();
				System.out.println("ja pierdole");
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectButton.setEnabled(false);
			reConnectButton.setEnabled(true);
		}
		//TODO wyswietl komunika ze port chujowy
	}

	private boolean portIsValid(int port){
		//TODO
		return true;
	}
	
	private String detectIP(){
		Enumeration<NetworkInterface> interfaces;
		String str = "";
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		
		while (interfaces.hasMoreElements()){
		    NetworkInterface current = interfaces.nextElement();
		    System.out.println(current);
		   // str +=current+"\n";
		    if (!current.isUp() || current.isLoopback() || current.isVirtual()){
		    	continue;
		    }
		    str +=current.getName()+"\n";
		    Enumeration<InetAddress> addresses = current.getInetAddresses();

		    while (addresses.hasMoreElements()){
		        InetAddress current_addr = addresses.nextElement();
		        if (current_addr.isLoopbackAddress()){
		        	continue;
		        }

		        if(current_addr instanceof Inet4Address){
		        	System.out.println(current_addr.getHostAddress());
		        	str += current_addr.getHostAddress()+"\n";
		        }
		    }
		}
	    	
		} catch (SocketException e) {
			return "Unknown";
		}
		return str;
	}

	private String detectOS(){
		return "Ubuntu";
	}
	private String detectBluetoothDev(){
		return "detected";
	}
}
