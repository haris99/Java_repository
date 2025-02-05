package ProjectHD;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SupportWindow {
	private final String splitter = "///";
	private JFrame frame;
	private JLabel statusLabel;
	private JFormattedTextField supporterTextField;
	private JCheckBox chckbxSound;
	JCheckBox chckbxMicrophone;
	ClientConnector clientConnector;
	private boolean running = false;
	private JLabel clientNameLabel;
	private JFormattedTextField ipTextField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupportWindow window = new SupportWindow();

					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SupportWindow() {
		initialize();
	}

	private void runConnection() {
		statusLabel.setText("Setting up streams");
		String ipAdress = "";
			//ipAdress = Inet4Address.getLocalHost().getHostAddress();
			ipAdress = ipTextField.getText();
		String userName = supporterTextField.getText();
		if(userName.isEmpty())
		{
			userName = "default";
		}
		int socket = 3000;
		// IMPLEMENT CONNECTION TO SERVER SO IT SAVES IP?
		// statusLabel.setText("Waiting for connection");
		ServerConnector connection = new ServerConnector(ipAdress, socket);
		connection.open();
		connection.writeMessage("supporter connect"+splitter+userName);
		connection.closeConnection();
		// Message message = connection.readMessage();
		statusLabel.setText("Connection established");
		// runConnection();
		clientConnector = new ClientConnector(3001);
		clientConnector.waitForConnection();
		clientConnector.receiveUserName();
		clientConnector.setUserName(userName);
		String clientUserName = clientConnector.getUserName();
		// NEED TO BE STARTED IN SEQUENCE CHANGE LATER
		clientConnector.startMicrophone();
		clientConnector.startSpeakers();
		chckbxSound.setEnabled(true);
		chckbxMicrophone.setEnabled(true);
		running = true;
		clientNameLabel.setText(clientUserName);
		System.out.println(clientUserName);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 534, 411);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		
		supporterTextField = new JFormattedTextField();
		supporterTextField.setBounds(10, 45, 177, 20);
		frame.getContentPane().add(supporterTextField);
		
		JLabel lblName = new JLabel("Enter your name:");
		lblName.setBounds(10, 20, 177, 14);
		frame.getContentPane().add(lblName);
		
		statusLabel = new JLabel("Start the connection");
		statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		statusLabel.setBounds(10, 149, 184, 20);
		frame.getContentPane().add(statusLabel);
		JButton waitButton = new JButton("WAIT FOR CONNECTION");
		waitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runConnection();
			}
		});
		waitButton.setBounds(10, 180, 219, 23);
		frame.getContentPane().add(waitButton);
		
		chckbxSound = new JCheckBox("Sound");
		chckbxSound.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(running == true){
				if (!chckbxSound.isSelected()) {
					clientConnector.suspendSpeakers();
				}
				else{
					clientConnector.resumeSpeakers();
				}
				}
			}
		});
		chckbxSound.setEnabled(false);
		chckbxSound.setSelected(true);
		chckbxSound.setBounds(10, 210, 97, 23);
		frame.getContentPane().add(chckbxSound);
		
		chckbxMicrophone = new JCheckBox("Microphone");
		chckbxMicrophone.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(running == true){
				if (!chckbxMicrophone.isSelected()) {
					clientConnector.suspendMicrophone();
				}
				else{
					clientConnector.resumeMicrophone();
				}
				}
			}
		});
		chckbxMicrophone.setEnabled(false);
		chckbxMicrophone.setSelected(true);
		chckbxMicrophone.setBounds(10, 236, 97, 23);
		frame.getContentPane().add(chckbxMicrophone);
		
		clientNameLabel = new JLabel("");
		clientNameLabel.setBounds(341, 20, 107, 14);
		frame.getContentPane().add(clientNameLabel);
		
		JLabel lblClientName = new JLabel("Client name:");
		lblClientName.setBounds(260, 20, 83, 14);
		frame.getContentPane().add(lblClientName);
		
		ipTextField = new JFormattedTextField();
		ipTextField.setText("192.168.10.118");
		ipTextField.setBounds(10, 103, 177, 20);
		frame.getContentPane().add(ipTextField);
		
		JLabel lblEnterServerIp = new JLabel("Enter server ip:");
		lblEnterServerIp.setBounds(10, 76, 177, 14);
		frame.getContentPane().add(lblEnterServerIp);
	}
}
