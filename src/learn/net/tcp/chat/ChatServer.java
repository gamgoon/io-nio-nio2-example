package learn.net.tcp.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;

public class ChatServer {
	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(10001);
			System.out.println("접속을 기다립니다.");
			HashMap hm = new HashMap();
			while (true) {
				Socket sock = server.accept();
				ChatThread chatThread = new ChatThread(sock, hm);
				chatThread.start();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

class ChatThread extends Thread{
	
	private Socket sock;
	private String id;
	private BufferedReader br;
	private HashMap hm;
	private boolean initFlag = false;
	
	public ChatThread(Socket sock, HashMap hm) {
		this.sock = sock;
		this.hm = hm;
		try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			id = br.readLine();
			broadcast(id + "님이 접속했습니다.");
		}catch(Exception e){
			System.out.println(e);
		}
	}

	private void broadcast(String msg) {
		synchronized (hm) {
			Collection collection = hm.values();
			
		}
	}
	
}