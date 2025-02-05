package ProjectHD;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Server {
	public static List<Supporter> supporterList=new ArrayList<>();
	public static  List<Client> clientList=new ArrayList<>();
	
	
public static void addClient(Client client){
	clientList.add(client);
}

public static void addSupporter(Supporter supporter){
	supporterList.add(supporter);
}

public static void removeClient(Client client){
	clientList.remove(client);
}

public static void removeSupporter(Supporter supporter){
	supporterList.remove(supporter);
}

	
	
	public static void main(String args[]) throws IOException {
		//clientList.add(new Client("Haris", "192.168.10.118"));
		ServerSocket serverSocket = new ServerSocket(3000);
		while (true) {
			Thread connectThread = new Thread(new ConnectThread(serverSocket.accept()));
			connectThread.start();
		}
	}
}

class ConnectThread implements Runnable {
	private volatile boolean done = false;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public static Collection<Socket> sockets = new ArrayList<Socket>();
	private Socket connection = null;
	DataInputStream dataIn = null;
	DataOutputStream dataOut = null;
	
	
	public ConnectThread(Socket socket) throws IOException{
		connection = socket;
		socket.getRemoteSocketAddress().toString();
	    ois = new ObjectInputStream(connection.getInputStream());
	    oos = new ObjectOutputStream(connection.getOutputStream());
	    sockets.add(connection);
	}

	public void start() {
		run();
	}

	public void run() {
		String result=null;
		// TODO Auto-generated method stub
		//ONLY IF ATLEAST 1 CLEINT AND SUPPORTER
		while(!connection.isClosed() && !done){
			String supporterIpAdress;
			if (Server.supporterList.size()>0){
			supporterIpAdress = Server.supporterList.get(0).getIpAdress();
			}else
			{
			InetSocketAddress isa = (InetSocketAddress) connection.getRemoteSocketAddress();
			InetAddress addr = isa.getAddress();
			supporterIpAdress=addr.toString();
			result = supporterIpAdress.substring(1, supporterIpAdress.length());
			System.out.println(result);
		}
			MessageHandler.handleMessage(ois, oos, result);
			closeConnection();
			//readMessage();
			try{
				Thread.sleep(100);
			} catch (InterruptedException ex){}
		}
	}
	
	public void closeConnection(){
		done = true;
	}
	
}
