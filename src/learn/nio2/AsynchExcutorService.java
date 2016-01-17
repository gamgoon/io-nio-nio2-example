package learn.nio2;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class AsynchExcutorService {

	private static Set<StandardOpenOption> withOptions() {
		final Set<StandardOpenOption> options = new TreeSet<>();
		options.add(StandardOpenOption.READ);
		return options;
	}
	
	public static void main(String[] args) {
		final int THREADS = 5;
		ExecutorService taskExecutor = Executors.newFixedThreadPool(THREADS);
		
		String encoding = System.getProperty("file.encoding");
		List<Future<ByteBuffer>> list = new ArrayList<>();
		int sheeps = 0;
		
		Path path = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example", "story.txt");
		
		try (AsynchronousFileChannel asynchronousFileChannel = 
				AsynchronousFileChannel.open(path, withOptions(), taskExecutor)){
			
			for(int i = 0; i < 50; i++) {
				Callable<ByteBuffer> worker = new Callable<ByteBuffer>() {

					@Override
					public ByteBuffer call() throws Exception {
						ByteBuffer buffer = ByteBuffer.allocateDirect(ThreadLocalRandom.current().nextInt(100, 200));
						asynchronousFileChannel.read(buffer, ThreadLocalRandom.current().nextInt(0, 100));
						return buffer;
					}
				};
				Future<ByteBuffer> future = taskExecutor.submit(worker);
				list.add(future);
			}
			
			// 익스큐터가 새로운쓰레드를 수락하지 않게 하고
			// 큐에 있는기존 스레드를 모두 끝낸다.
			taskExecutor.shutdown();
			
			// 쓰레드가 모두 끝날 떄까지 대기.
			while (!taskExecutor.isTerminated()) {
				// 버퍼가 준비될 떄까지 다른 것을 한다.
				System.out.println("Counting sheep while filling up some buffers! So far I counted: " + (sheeps +=1));
			}
			
			System.out.println("\nDone! Here are the buffers:\n");
			for (Future<ByteBuffer> future : list) {
				ByteBuffer buffer = future.get();
				System.out.println("\n\n"+buffer);
				System.out.println("____________________________________________________________");
				buffer.flip();
				System.out.println(Charset.forName(encoding).decode(buffer));
				buffer.clear();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
