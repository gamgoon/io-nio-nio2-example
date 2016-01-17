package learn.nio2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * 비동기 파일 쓰기 
 * @author gamgoon
 *
 */
public class AsynchFileChannelForWrite {

	public static void main(String[] args) {
		
		ByteBuffer buffer = ByteBuffer.wrap("The win keeps Nadal at the top of the heap in men's tennis, at least for a few more weeks. The world No2, Novak Djokovic, duped out here in the semi-finals by a resurgent Federer, will come hard at them again at Wimbledon but there is moch to come from two rivals who, for seven years, have held all pretenders at bay.".getBytes());
		
		Path path = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example", "story.txt");		
		
		// 파일 없으면 생성.
		if(Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
			FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
			try {
				Files.createFile(path, attr);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
			
		try (AsynchronousFileChannel asynchronousFileChannel = 
				AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)){
			
			Future<Integer> result = asynchronousFileChannel.write(buffer, 100);
			
			while (!result.isDone()) {
				System.out.println("Do something else while writing...");
			}
			
			System.out.println("Written done: " + result.isDone());
			System.out.println("Bytes written: " + result.get());
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
