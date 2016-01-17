package learn.io.bytestream;

import java.io.IOException;

public class SystemInputTest {
	public static void main(String[] args) {
		int i = 0;
		
		try {
			while (true) {
				i = System.in.read();
				System.out.println(i);
				if(i == -1){
					break;
				}
				System.out.write(i);
			}
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
