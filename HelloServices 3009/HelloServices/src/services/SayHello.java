package services;

public class SayHello {
	private ConnectServerSocket connectServerSocket= ConnectServerSocket.getIntance();
	public String sayHello(String s){
		return s;
	}
	public synchronized String tokenizerService(String sequence){
//		System.out.println("Msg: "+sequence);
		return connectServerSocket.process(sequence);
	}
}
