package learn.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 다중 클라이언트 처리 - ExecutorService 로 래핑 
 * @author gamgoon
 *
 */
public class EchoServerExecutor {

	public static void main(String[] args) {
		final int DEFAULT_PORT = 5555;
		final String IP = "127.0.0.1";
		ExecutorService taskExecutor =
				Executors.newCachedThreadPool(Executors.defaultThreadFactory());
		
		// 기본 그룹에 바인딩된 비동기 서버 소켓 채널을 생성한다.
		try(AsynchronousServerSocketChannel asynchronousServerSocketChannel =
				AsynchronousServerSocketChannel.open();) {
			if(asynchronousServerSocketChannel.isOpen()){
				// 몇가지 옵션을 설정 
				asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
				asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
				
				//비동기 서버 소켓 채널을 로컬 주소에 바인딩
				asynchronousServerSocketChannel.bind(new InetSocketAddress(IP, DEFAULT_PORT));
				
				// 클라이언트를 기다린다는 대기 메시지를 표시.
				System.out.println("Waiting for connection ...");
				while (true) {
					Future<AsynchronousSocketChannel> asynchronouseSocketChannelFuture =
							asynchronousServerSocketChannel.accept();
					
					try{
						
						final AsynchronousSocketChannel asynchronousSocketChannel =
								asynchronouseSocketChannelFuture.get();
						Callable<String> worker = new Callable<String>(){

							@Override
							public String call() throws Exception {
								String host = asynchronousSocketChannel.getRemoteAddress().toString();
								System.out.println("Incoming connection from: " + host);
								final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
								System.out.println("11");
								// 데이터 전송
								while (asynchronousSocketChannel.read(buffer).get() != -1) {
									System.out.println("22");
									buffer.flip();
									asynchronousSocketChannel.write(buffer).get();
									System.out.println("33");
									if(buffer.hasRemaining()){
										System.out.println("compact");
										buffer.compact();
									}else{
										System.out.println("clear");
										buffer.clear();
									}
									System.out.println("44");
								}
								
								asynchronousSocketChannel.close();
								System.out.println(host + " as successfully served!");
								return host;
							}
						};
							
						taskExecutor.submit(worker);
							
					}catch (InterruptedException | ExecutionException ex) {
						System.err.println(ex);
						System.err.println("\n Server is shutting down ...");
						
						// 익스큐터가 새스레드를 수락하지 못하게 하고
						// 큐에 있는 스레드를 모두 끝낸다.
						taskExecutor.shutdown();
						
						//모든 스레드가 끝날 떄까지 대기한다.
						while (!taskExecutor.isTerminated()) {
							
						}
						break;
					}						
					
				}
			}else{
				System.out.println("The asynchronous server-socket channel cannot be opened!");
			}
		} catch(IOException ex) {
			System.err.println(ex);
		}
	}

}
