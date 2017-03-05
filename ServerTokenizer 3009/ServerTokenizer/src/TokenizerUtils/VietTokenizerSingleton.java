package TokenizerUtils;


import vn.hus.nlp.tokenizer.VietTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SEV_USER
 */
public class VietTokenizerSingleton {
    private VietTokenizer vietTokenizer;
    public static VietTokenizerSingleton vietTokenizerSingleton;
    public VietTokenizerSingleton() {
        vietTokenizer=new VietTokenizer();
    }
    public static VietTokenizerSingleton getInstance(){
        if(vietTokenizerSingleton==null)
            vietTokenizerSingleton=new VietTokenizerSingleton();
        return vietTokenizerSingleton;
    }
    public String processTokenizer(String sequence){
        return vietTokenizer.segment(sequence);
    }
    
}
