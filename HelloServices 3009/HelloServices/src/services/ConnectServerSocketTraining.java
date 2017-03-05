package services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectServerSocketTraining {
	public  int PORT = 1994;
	public  String host = "localhost";
	public  Socket socket = null;
//	public  DataInputStream dis = null;
	public  DataOutputStream dos = null;
	public static ConnectServerSocketTraining connectServerSocket;
	public ConnectServerSocketTraining() {
		// TODO Auto-generated constructor stub
		try {
			socket = new Socket(host, PORT);
//			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("TAG-ERROR:" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TAG-ERROR:" + e.getMessage());
		}
	}

	public String process(String msg) {
		String result = "";
		try {
			dos.writeUTF(msg);
//			result = dis.readUTF();
//			System.out.println("Resp: "+result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TAG-ERROR:" + e.getMessage());
		}
		return result;
	}
	public static ConnectServerSocketTraining getIntance(){
		if(connectServerSocket==null){
			connectServerSocket=new ConnectServerSocketTraining();
		}
		return connectServerSocket;
	}
}
