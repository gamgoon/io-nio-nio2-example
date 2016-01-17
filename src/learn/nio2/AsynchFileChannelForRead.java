package learn.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * 비동기 파일 읽기
 * @author gamgoon
 *
 */
public class AsynchFileChannelForRead {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(100);
		String encoding = System.getProperty("file.encoding");
		
		Path path = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example", "BNP.txt");
		
		try (AsynchronousFileChannel asynchronousFileChannel = 
				AsynchronousFileChannel.open(path, StandardOpenOption.READ)){
			Future<Integer> result = asynchronousFileChannel.read(buffer, 0);
			
			while (!result.isDone()) {
				System.out.println("Do something else while reading ...");
			}
			
			System.out.println("Read done : "+ result.isDone());
			System.out.println("Bytes read : " + result.get());
		} catch (Exception e) {
			System.err.println(e);
		}
		
		buffer.flip();
		System.out.println(Charset.forName(encoding).decode(buffer));
		buffer.clear();
	}

}
