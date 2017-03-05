/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author SEV_USER
 */
public class ClientService {

    private Socket socket = null;
    private final int PORT = 5000;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    public ClientService() {
    }

    public void connectServer() throws UnknownHostException, IOException {
        InetAddress ipAddress = InetAddress.getByName("192.168.56.101");
        socket = new Socket(ipAddress, PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public String requestFromServer(String msg) throws IOException {
        String result="";
        byte[] m = msg.getBytes();
        dos.write(m);
        byte[] buffer = new byte[1024];
        int length = 0;
        if ((length = dis.read(buffer)) > 0) {
            msg = new String(buffer,0,length);
            result=msg.trim();
//            System.out.println("Msg-response 1: "+result);
        }
//        System.out.println("Msg-response: "+result);
        return result;
    }
}
