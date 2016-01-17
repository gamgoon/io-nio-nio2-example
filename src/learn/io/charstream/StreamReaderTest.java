package learn.io.charstream;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StreamReaderTest {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("사용법 : java StreamReaderTest 파일명");
			System.exit(0);
		}
		
		try (
				FileInputStream fis = new FileInputStream(args[0]);
				InputStreamReader isr = new InputStreamReader(fis);
				OutputStreamWriter osw = new OutputStreamWriter(System.out);
			) {
			
			char[] buffer = new char[512];
			int readcount = 0;
			while ((readcount = isr.read(buffer)) != -1) {
				osw.write(buffer, 0, readcount);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
