package learn.io.bytestream;

import java.io.FileInputStream;

public class FileView {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("사용법 : java FileView 파일명");
			System.exit(0);
		}
		
		try ( FileInputStream fis = new FileInputStream(args[0]);){
			
			int i = 0;
			while ((i = fis.read()) != -1) {
				System.out.write(i);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
