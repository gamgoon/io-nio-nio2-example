package learn.io.bytestream;

import java.io.FileInputStream;

public class FileView2 {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("사용법 : java FileView 파일명");
			System.exit(0);
		}
		
		try ( FileInputStream fis = new FileInputStream(args[0]);){
			
			int readcount = 0;
			byte[] buffer = new byte[512];
			while ((readcount = fis.read(buffer)) != -1) {
				System.out.write(buffer, 0, readcount);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
