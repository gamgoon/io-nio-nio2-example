package learn.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsynchFileChannelCompletionHandler {
	
	static Thread current;
	
	public static void main(String[] args) {
		
		ByteBuffer buffer = ByteBuffer.allocate(100);
		Path path = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example", "story.txt");
		
		try(AsynchronousFileChannel asynchronousFileChannel =
				AsynchronousFileChannel.open(path, StandardOpenOption.READ)){
			
			current = Thread.currentThread();
			asynchronousFileChannel.read(buffer, 0, "Read operation status ...", new CompletionHandler<Integer, Object>(){

				@Override
				public void completed(Integer result, Object attachment) {
					System.out.println(attachment);
					System.out.println("Read bytes : " + result);
					current.interrupt();
				}

				@Override
				public void failed(Throwable exc, Object attachment) {
					System.out.println(attachment);
					System.out.println("Error : " + exc);
					current.interrupt();
				}
				
			});
			
			System.out.println("\nWaiting for reading operation to end ...\n");
			try {
				current.join();
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			
			System.out.println("\n\nClose everything and leave! Byte, bye ...");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
