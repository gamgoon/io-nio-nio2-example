package learn.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;

public class FileTest {

	public static void main(String[] args) throws URISyntaxException, IOException {
//		
//		Path path = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Download");
//		System.out.println(path.toString());
//		URI path_to_uri = path.toUri();
//		System.out.println(path_to_uri);
//		
//		Path path2 = Paths.get("/Download", "source");
//		System.out.println(path2.toString());
//		
//		Path path2_to_absolute_path = path2.toAbsolutePath();
//		System.out.println(path2_to_absolute_path.toString());
//		
//		Path real_path = path2.toRealPath(LinkOption.NOFOLLOW_LINKS);
//		System.out.println(real_path.toString());
//		
//		
		Path path01 = Paths.get("/Users/gamgoon/development/workspace-sts/io-nio-example/BNP.txt");
		Path path02 = Paths.get("/gamgoon/development/workspace-sts/io-nio-example/BNP.txt");
		
		Path path01_to_absolute_path = path01.toAbsolutePath();
		Path path02_to_absolute_path = path02.toAbsolutePath();
		
		System.out.println(path02.getRoot().toString());
		
		System.out.println(path01_to_absolute_path);
		System.out.println(path02_to_absolute_path);
		
		if(path01.equals(path02)){
			System.out.println("The paths are equal!");
		}else{
			System.out.println("The paths are not equal!");
		}
//		
//		FileSystem fs = FileSystems.getDefault();
//		
//		String sp = fs.getSeparator();
//		System.out.println(sp);
//		
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		Iterable<Path> it = fs.getRootDirectories();
//		for(Path root : it){
//			System.out.println(root + " : ");
//			try {
//		        FileStore store = Files.getFileStore(root);
//		        System.out.println("available=" + nf.format(store.getUsableSpace())
//		                            + ", total=" + nf.format(store.getTotalSpace()));
//		    } catch (IOException e) {
//		        System.out.println("error querying space: " + e.toString());
//		    }
//		}
	}

}
