package learn.thread;

public class DeamonThreadTest {
	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run() {
				try{
					Thread.sleep(5000);
					System.out.println("My Thread 종료");
				} catch(Exception e) {
					//
				}
			}
		};
		t.setDaemon(true);
		t.start();
		
		System.out.println("main() 종료");
	}
}
