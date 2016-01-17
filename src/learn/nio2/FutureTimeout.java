package learn.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 파일 읽기와 Future 타임아웃 
 * @author gamgoon
 *
 */
public class FutureTimeout {

	public static void main(String[] args) {
		
		ByteBuffer buffer = ByteBuffer.allocate(100);
		int bytesRead = 0;
		Future<Integer> result = null;
		Path path = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example","story.txt");
		
		try (AsynchronousFileChannel asynchronouseFileChannel =
				AsynchronousFileChannel.open(path, StandardOpenOption.READ)){
			
			result = asynchronouseFileChannel.read(buffer, 0);
			bytesRead = result.get(1000000000, TimeUnit.NANOSECONDS);
			if(result.isDone()) {
				System.out.println("The result is available!");
				System.out.println("Read bytes: "+ bytesRead);
			}
		} catch (Exception e) {
			if (e instanceof TimeoutException) {
				if (result != null) {
					result.cancel(true);
				}
				System.out.println("The result is not available!");
				System.out.println("The read task was cancelled ? " + result.isCancelled());
				System.out.println("Read bytes : " + bytesRead);
				
			}else{
				System.err.println(e);
			}
		}
	}

}
