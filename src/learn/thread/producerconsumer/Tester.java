package learn.thread.producerconsumer;

public class Tester {
	public static void main(String[] args) throws InterruptedException {
		// 큐를 생성한다
		Queue queue = JobQueue.getInstance();
		
		// 소비자를 생성하고 시작한다
		Thread con1 = new Thread(new Consumer(queue, "1"));
		Thread con2 = new Thread(new Consumer(queue, "2"));
		Thread con3 = new Thread(new Consumer(queue, "3"));
		con1.start();
		con2.start();
		con3.start();
		
		// 생성자를 생성하고 시작한다
		Thread pro = new Thread(new Producer(queue));
		pro.start();
		
		// 0.5초 간 멈춘다.
		Thread.sleep(500);
		// 생성자를 종료시킨다.
		pro.interrupt();
		
		// 0.5초 간 멈춘다.
		Thread.sleep(500);
		// 소비자를 종료시킨다.
		con1.interrupt();
		con2.interrupt();
		con3.interrupt();
	}
}
