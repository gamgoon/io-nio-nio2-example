package learn.net.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServer {

	public static void main(String[] args) {
		try{
			ServerSocket ss = new ServerSocket(8001);
			
			while (true) {
				System.out.println("접속을 기다립니다.");
				Socket sock = ss.accept();
				System.out.println("새로운 스레드를 시작합니다.");
				HttpThread ht = new HttpThread(sock);
				ht.start();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}

class HttpThread extends Thread {
	private Socket sock = null;
	BufferedReader br = null;
	PrintWriter pw = null;
	
	public HttpThread(Socket sock) {
		this.sock = sock;
		try{
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void run() {
		BufferedReader fbr = null;
		try {
			String line = br.readLine();
			int start = line.indexOf(" ") + 2;
			int end = line.lastIndexOf("HTTP") -1;
			String filename = line.substring(start,end);
			
			if(filename.equals("")){
				filename = "index.html";
			}
			
			System.out.println("사용자가 "+ filename + "을 요청했습니다.");
			
			fbr = new BufferedReader(new FileReader(filename));
			String fline = null;
			while((fline = fbr.readLine()) != null) {
				pw.println(fline);
				pw.flush();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try{
				if(fbr != null) fbr.close();
			}catch(Exception e){}
			try{
				if(br != null) br.close();
			}catch(Exception e){}
			try{
				if(pw != null) pw.close();
			}catch(Exception e){}
			try{
				if(sock != null) sock.close();
			}catch(Exception e){}
		}
	}
}
