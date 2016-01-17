package learn.thread.producerconsumer;

public class Consumer implements Runnable {
	private Queue queue = null;
	private String name = null;
	
	public Consumer(Queue queue, String index) {
		this.queue = queue;
		name = "Consumer-" + index;
	}
	@Override
	public void run() {
		System.out.println("[ Start " + name + ".. ]");
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println(name + " : " + queue.pop().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("[ End " + name + ".. ]");
		}
	}

}
