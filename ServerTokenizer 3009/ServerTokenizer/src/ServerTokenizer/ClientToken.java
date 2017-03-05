/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerTokenizer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author SEV_USER
 */
public class ClientToken {
    public  static int PORT=1993;
    public static String host="localhost";
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        Socket socket=null;
        DataInputStream dis=null;
        DataOutputStream dos=null;
        try {
            socket= new Socket(host, PORT);
            dis=new DataInputStream( socket.getInputStream());
            dos= new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            String send=null;
            while((send=in.readLine()).length()!=0){
                dos.writeUTF(send);
                System.out.println("From server: "+dis.readUTF());
                if (send.equals("bye")) {
                    break;
                }
                System.out.println("Enter message: ");
                
            }
            dos.close();dis.close();socket.close();
        } catch (IOException ex) {
            System.out.println("ERROR:"+ex);
        }
    }
}
