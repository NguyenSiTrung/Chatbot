package services;

import vn.hus.nlp.tokenizer.VietTokenizer;

public class TokenUtils {
	public static VietTokenizer vietTokenizer;
	public static String tokenizer(String s){
		if(vietTokenizer==null)
			vietTokenizer=new VietTokenizer();
		return vietTokenizer.segment(s);
	}
	public static void main(String[] args) {
		String s="Học sinh học sinh học";
		System.out.println(TokenUtils.tokenizer(s));
	}
}
