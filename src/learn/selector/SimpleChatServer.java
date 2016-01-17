package learn.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleChatServer {
	
	private static final String HOST = "localhost";
	private static final int PORT = 9090;
	
	private static FileHandler fileHandler;
	private static Logger logger = Logger.getLogger("learn.selector");
	
	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private ServerSocket serverSocket = null;
	
	private Vector room = new Vector();
	
	public void initServer() {
		try {
			// open selector
			selector = Selector.open();
			
			// create server socket channel
			serverSocketChannel = ServerSocketChannel.open();
			// non-blocking mode
			serverSocketChannel.configureBlocking(false);
			// get socket from server socket channel
			serverSocket = serverSocketChannel.socket();
			
			// bind address and port
			InetSocketAddress isa = new InetSocketAddress(HOST, PORT);
			serverSocket.bind(isa);
			
			// register server socket channel to selector
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
		} catch (IOException e) {
			log(Level.WARNING, "SimpleChatServer.initServer()", e);
		}
	}
	
	public void startServer() {
		info("Server is started..");
		try {
			while (true) {
				info("waiting request..");
				
				// check event (ready) by select() method from selector
				selector.select();
				
				// 
				Iterator it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					if (key.isAcceptable()) {
						// try connect to server socket channel from client
						accept(key);
					} else if (key.isReadable()) {
						// sending message from client aleady connectied
						read(key);
					}
					// have to delete
					it.remove();
					
				}
			}
		} catch (Exception e) {
			log(Level.WARNING, "SimpleChatServer.startServer()", e);
		}
	}
	
	private void accept(SelectionKey key) {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		SocketChannel sc;
		try {
			// create server socket
			sc = server.accept();
			// register socket channel created to selector 
			registerChannel(selector,sc, SelectionKey.OP_READ);
			info(sc.toString() + " 클라이언트가 접속했습니다.");
		} catch (ClosedChannelException e) {
			log(Level.WARNING, "SimpleChatServer.accept()", e);
		} catch (IOException e) {
			log(Level.WARNING, "SimpleChatServer.accept()", e);
		}
	}

	private void registerChannel(Selector selector, SocketChannel sc, int ops) throws IOException {
		if (sc == null) {
			info("Invalid Connection");
			return;
		}
		sc.configureBlocking(false);
		sc.register(selector, ops);
		// add to Chat Room
		addUser(sc);
	}
	
	private void addUser(SocketChannel sc) {
		room.add(sc);
	}

	private void read(SelectionKey key) {
		
		// get socket channel from SelectionKey
		SocketChannel sc = (SocketChannel) key.channel();
		// create ByteBuffer
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		try {
			
			int read = sc.read(buffer);
			info(read + " byte를 읽었습니다.");
		} catch (IOException e) {
			try {
				sc.close();
			} catch (IOException e2) {
			}
			removeUser(sc);
			info(sc.toString() + " 클라이언트가 접속을 해제했습니다.");
		}
		
		try {
			breadcast(buffer);
		} catch (IOException e) {
			log(Level.WARNING, "SimpleChatServer.broadcast()", e);
		}
		
		// release buffer memory
		clearBuffer(buffer);
	}

	private void removeUser(SocketChannel sc) {
		room.remove(sc);
	}

	private void clearBuffer(ByteBuffer buffer) {
		if (buffer != null) {
			buffer.clear();
			buffer = null;
		}
	}

	private void breadcast(ByteBuffer buffer) throws IOException {
		buffer.flip();
		
		Iterator iter = room.iterator();
		while (iter.hasNext()) {
			SocketChannel sc = (SocketChannel) iter.next();
			if (sc != null) {
				sc.write(buffer);
				buffer.rewind();
			}
		}
	}
	
	// Log part
	public void initLog() {
		try {
			fileHandler = new FileHandler("SimpleChatServer.log");
		} catch (IOException e) {
			logger.addHandler(fileHandler);
			logger.setLevel(Level.ALL);
		}
	}
	
	public void log(Level level, String msg, Throwable error) {
		logger.log(level, msg, error);
	}
	
	public void info(String msg) {
		logger.info(msg);
	}
	
	// Main
	public static void main(String[] args) {
		SimpleChatServer scs = new SimpleChatServer();
		scs.initLog();
		scs.initServer();
		scs.startServer();
	}
}
