/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientFromMobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import vn.hus.nlp.tokenizer.VietTokenizer;

/**
 *
 * @author SEV_USER
 */
public class ListenServerRequest extends Thread {

    DataOutputStream out = null;
    DataInputStream in = null;
    VietTokenizer vietTokenizer = null;

    public ListenServerRequest(DataOutputStream out, DataInputStream in, VietTokenizer vietTokenizer) {
        this.out = out;
        this.in = in;
        this.vietTokenizer = vietTokenizer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String getsend = "";
                getsend = in.readUTF();
                if (getsend != null) {
                    getsend = vietTokenizer.segment(getsend);
                    out.writeUTF(getsend);
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
