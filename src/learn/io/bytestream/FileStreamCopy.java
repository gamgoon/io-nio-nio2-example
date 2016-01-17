package learn.io.bytestream;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileStreamCopy {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("사용법 : java FileView 파일1 파일2");
			System.exit(0);
		}
		
		try ( 
				FileInputStream fis = new FileInputStream(args[0]);
				FileOutputStream fos = new FileOutputStream(args[1]);
				){
			
			int readcount = 0;
			byte[] buffer = new byte[512];
			while ((readcount = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, readcount);
			}
			System.out.println("복사가 완료되었습니다.");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
