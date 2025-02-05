package ProjectHD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnector extends Connector {
	private int port;
	private ServerSocket serverSocket;
	//private Socket acceptSocket;
	private boolean running = false;
	private String userName;

	public ClientConnector(int port) {
		this.port = port;
	}

	
	public void waitForConnection() {
			try {
				serverSocket = new ServerSocket(port);
				connection = serverSocket.accept();
				oos = new ObjectOutputStream(connection.getOutputStream());
				ois = new ObjectInputStream(connection.getInputStream());
				running = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean isRunning(){
		return running;
	}
	
	
	
}
