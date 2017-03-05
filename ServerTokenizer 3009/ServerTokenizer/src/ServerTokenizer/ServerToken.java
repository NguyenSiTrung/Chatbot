/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerTokenizer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author SEV_USER
 */
public class ServerToken {

    public final int PORT = 1234;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    public VietTokenizer vietTokenizer;

    public ServerToken(VietTokenizer vietTokenizer) {
        this.vietTokenizer=vietTokenizer;
    }
    
    class Client implements Runnable {

        private Socket cliecntSocket;

        public Client(Socket socket) {
            this.cliecntSocket=socket;
        }
        
        @Override
        public void run() {
            DataInputStream dis = null;
            DataOutputStream dos = null;
            String msg = null;
            try {
                dis = new DataInputStream(cliecntSocket.getInputStream());
                dos = new DataOutputStream(cliecntSocket.getOutputStream());
                while (true) {
                    msg = dis.readUTF();
                    System.out.println("MSG:"+msg);
                    msg = vietTokenizer.segment(msg);
                    dos.writeUTF(msg);
                }
            } catch (IOException ex) {
                System.out.println("Erro:" + ex.getMessage());
            }
        }

    }
    public void runServer(){
         Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    while (true) {
                        Client mc;
                        mc = new Client(serverSocket.accept());
                        System.out.println("connect ");
                        Thread tr = new Thread(mc);
                        tr.start();
                    }
                } catch (IOException ex) {
                  System.out.println("Erro:" + ex.getMessage());  
                }
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        thread.start();
    }
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        VietTokenizer vietTokenizer=new VietTokenizer();
       ServerToken serverToken=new ServerToken(vietTokenizer);
       serverToken.runServer();
    }
}
