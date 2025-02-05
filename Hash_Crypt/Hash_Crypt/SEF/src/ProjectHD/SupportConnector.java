package ProjectHD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SupportConnector extends Connector {
	private String ipAdress;
	private int port;
	//private Socket connection;

	public SupportConnector(String ipAdress, int port) {
			this.ipAdress = ipAdress;
			this.port = port;
	}

	public void connect() {
		try {
			connection = new Socket(ipAdress, port);
			oos = new ObjectOutputStream(connection.getOutputStream());
			ois = new ObjectInputStream(connection.getInputStream());
			//oos.writeObject(new Message("connect to room", null));
			//oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startSpeakers() {
		speaker = new AudioInput(connection);
		Thread receiverThread = new Thread(speaker);
		receiverThread.start();
	}

	public void startMicrophone() {
		microphone = new AudioOutput(connection);
		Thread senderThread = new Thread(microphone);
		senderThread.start();
	}

	public void suspendSpeakers() {
		speaker.pause();
	}

	public void suspendMicrophone() {
		microphone.pause();
	}

	public void resumeSpeakers() {
		speaker.resume();
	}

	public void resumeMicrophone() {
		microphone.resume();
	}
	
	
	
}
