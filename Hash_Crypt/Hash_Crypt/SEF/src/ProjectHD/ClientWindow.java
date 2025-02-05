package ProjectHD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class ClientWindow {

	private JFrame frmClientWindow;
	ObjectOutputStream supportObjectOutputStream;
	ObjectInputStream supportObjectInputStream;
	JLabel statusLabel;
	JLabel lblEnterYourUsername;
	JFormattedTextField ipTextField;
	JFormattedTextField userNameTextField;
	private JCheckBox chckbxSound;
	private JCheckBox chckbxMicrophone;
	private boolean running = false;
	SupportConnector supportConnector;
	private final String splitter = "///";
	private JLabel supportNameLabel;
	private JLabel lblNewLabel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frmClientWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	private void runConnection(){
		String userName = userNameTextField.getText();
		String ipAdress = ipTextField.getText();
		int socket = 3000;
			statusLabel.setText("Waiting for connection");
			ServerConnector serverConnector = new ServerConnector (ipAdress, socket);
			serverConnector.open();
			if(userName.isEmpty())
			{
				userName = "default";
			}
			serverConnector.writeMessage("client connect"+splitter+userName);
			Message message = serverConnector.readMessage();
			serverConnector.closeConnection();
			statusLabel.setText("Connection established");
			//NEED TO CLOSE CONNECTION?
			//runConnection();
			
			
			//System.out.println(message.getMessageText());
			//MAKE SUPPORT CONNECTOR
			supportConnector = new SupportConnector (message.getMessageText(), 3001);
			supportConnector.connect();
			supportConnector.setUserName(userName);
			supportConnector.receiveUserName();
			String supportUserName = supportConnector.getUserName();
			//Socket connectionToSupport;
				supportConnector.startMicrophone();
				supportConnector.startSpeakers();
				statusLabel.setText("Communication underway");
				chckbxSound.setEnabled(true);
				chckbxMicrophone.setEnabled(true);
				running = true;
				supportNameLabel.setText(supportUserName);
			//connection
			//System.out.println("client");

	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClientWindow = new JFrame();
		frmClientWindow.setTitle("Client window");
		frmClientWindow.setBounds(100, 100, 536, 410);
		frmClientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClientWindow.getContentPane().setLayout(null);

		statusLabel = new JLabel("");
		statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		statusLabel.setBounds(58, 283, 361, 78);
		frmClientWindow.getContentPane().add(statusLabel);
		
		ipTextField = new JFormattedTextField();
		ipTextField.setText("192.168.10.118");
		ipTextField.setBounds(10, 92, 210, 20);
		frmClientWindow.getContentPane().add(ipTextField);

		JLabel lblEnterTheIp = new JLabel("Enter the ip adress of the server");
		lblEnterTheIp.setBounds(10, 67, 188, 14);
		frmClientWindow.getContentPane().add(lblEnterTheIp);

		lblEnterYourUsername = new JLabel("Enter your username");
		lblEnterYourUsername.setBounds(10, 11, 134, 14);
		frmClientWindow.getContentPane().add(lblEnterYourUsername);

		userNameTextField = new JFormattedTextField();
		userNameTextField.setBounds(10, 36, 141, 20);
		frmClientWindow.getContentPane().add(userNameTextField);

		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runConnection();
			}
		});
		btnNewButton.setBounds(10, 123, 89, 23);
		frmClientWindow.getContentPane().add(btnNewButton);
		
		chckbxSound = new JCheckBox("Sound");
		chckbxSound.setSelected(true);
		chckbxSound.setEnabled(false);
		chckbxSound.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(running == true){
					if (!chckbxSound.isSelected()) {
						supportConnector.suspendSpeakers();
					}
					else{
						supportConnector.resumeSpeakers();
					}
					}
			}
		});
		chckbxSound.setBounds(10, 153, 97, 23);
		frmClientWindow.getContentPane().add(chckbxSound);
		
		chckbxMicrophone = new JCheckBox("Microphone");
		chckbxMicrophone.setSelected(true);
		chckbxMicrophone.setEnabled(false);
		chckbxMicrophone.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(running == true){
					if (!chckbxSound.isSelected()) {
						supportConnector.suspendMicrophone();
					}
					else{
						supportConnector.resumeMicrophone();
					}
					}
			}
		});
		chckbxMicrophone.setBounds(10, 179, 97, 23);
		frmClientWindow.getContentPane().add(chckbxMicrophone);
		
		supportNameLabel = new JLabel("");
		supportNameLabel.setBounds(333, 11, 114, 14);
		frmClientWindow.getContentPane().add(supportNameLabel);
		
		lblNewLabel = new JLabel("Supporter name:");
		lblNewLabel.setBounds(215, 11, 119, 14);
		frmClientWindow.getContentPane().add(lblNewLabel);
		

	}
}
