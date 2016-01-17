package learn.thread.producerconsumer;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class JobQueue implements Queue {
	private static final String NAME = "JOB QUEUE";
	private static final Object monitor = new Object(); //
	
	private LinkedList jobs = new LinkedList();
	
	private static JobQueue instance = new JobQueue();
	private JobQueue() {}
	
	public static JobQueue getInstance(){
		if (instance == null) {
			synchronized (JobQueue.class) { // 
				instance = new JobQueue();
			}
		}
		return instance;
	}
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void clear() {
		synchronized (monitor) {
			jobs.clear();
		}
	}

	@Override
	public void put(Object o) {
		synchronized (monitor) {
			jobs.addLast(o);
			// 새로운 데이터가 추가되어 큐가 이용할 수 있으므로
			// 만약, 대기중인 스레드가 있다면 꺠운다.
			monitor.notify();
		}
	}

	@Override
	public Object pop() throws InterruptedException, NoSuchElementException {
		Object o = null;
		synchronized (monitor) {
			// 더 이상 큐에서 이용할 수 있는 데이터가 없으므로 스레드를 대기시킨다.
			if (jobs.isEmpty()) {
				monitor.wait();
			}
			o = jobs.removeFirst();
		}
		if (o == null) {
			throw new NoSuchElementException();
		}
		return o;
	}

	@Override
	public int size() {
		return jobs.size();
	}

}
