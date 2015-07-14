package ProjectHD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnector {
	private Socket connection;
	private ObjectOutputStream serverObjectOutputStream;
	private ObjectInputStream serverObjectInputStream;
	
	
	
	public ServerConnector(String ipAdress, int socket){
		try {
			connection = new Socket(ipAdress, socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void open(){
		try {
			serverObjectOutputStream = new ObjectOutputStream(connection.getOutputStream());
			serverObjectInputStream = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeMessage(String string){
		try {
			serverObjectOutputStream.writeObject(new Message(string, null));
			serverObjectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Message readMessage (){
		Message message=null;
		try {
			message = (Message) serverObjectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	
	public void closeConnection(){
		try {
			serverObjectOutputStream.close();
			serverObjectInputStream.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
