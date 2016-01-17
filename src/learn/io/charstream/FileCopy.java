package learn.io.charstream;

import java.io.FileReader;
import java.io.FileWriter;

public class FileCopy {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("사용법 : java StreamReaderTest 파일명1 파일명2");
			System.exit(0);
		}
		
		try(
			FileReader fr = new FileReader(args[0]);
			FileWriter fw = new FileWriter(args[1]);
			) {
			
			char[] buffer = new char[512];
			int readcount = 0;
			while ((readcount = fr.read(buffer)) != -1) {
				fw.write(buffer, 0, readcount);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
