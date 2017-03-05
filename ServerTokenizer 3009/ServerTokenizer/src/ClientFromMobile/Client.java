/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientFromMobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author SEV_USER
 */
public class Client {
    static private Socket mClient;
	static DataOutputStream  out;
	static DataInputStream in;
//	static ArrayList<String> ds = new ArrayList<String>();
//	static Queue<String> queue = new LinkedList<String>();
        static VietTokenizer vietTokenizer;
	static void startclient() {
		try{
                        vietTokenizer=new VietTokenizer();
			wakeUpServer(1993);
			mClient = new Socket("127.0.0.1", 1993);
			out = new DataOutputStream(mClient.getOutputStream());
			in = new DataInputStream(mClient.getInputStream());
                        new ListenServerRequest(out, in, vietTokenizer).start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
        private static void wakeUpServer(int port) {
		try {
			Runtime.getRuntime().exec("adb forward tcp:" + port + " tcp:" + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
        public static void main(String[] args) {
        startclient();
    }
}
