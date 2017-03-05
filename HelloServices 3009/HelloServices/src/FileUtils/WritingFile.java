package FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WritingFile {
	public static void writingFile(String context,String filename){
		try {
//			FileWriter out=new FileWriter(filename,true);
			PrintWriter out=new PrintWriter(new FileWriter(new File(filename),true));
			out.append(context);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
