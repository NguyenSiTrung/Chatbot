/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileUtlis;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SEV_USER
 */
public class WriteFileUtlis {
    public static void writingFile(String context,String filename){
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename,true), "UTF8"));
            out.append(context+"\n");
            out.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WriteFileUtlis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteFileUtlis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteFileUtlis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
