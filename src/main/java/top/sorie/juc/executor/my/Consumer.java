package top.sorie.juc.executor.my;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * todo 问题
 * @author soriee
 * @date 2025/2/23 15:56
 */
public class Consumer extends Thread {
	private ArrayBlockingQueue<String> queue;
	private List<Subscriber> subscribers;
	private ReentrantLock lock;
	private Condition condition;
	public Consumer(int queueSize) {
		this.queue = new ArrayBlockingQueue<>(queueSize);
		this.subscribers = new ArrayList<>(10);
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
	}

	public void subscribe(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	public void event(String name) {
		lock.lock();
		queue.add(name);
		condition.signal();
		lock.unlock();
	}

	@Override
	public void run() {
		while (true) {
			boolean locked = false;
			try {
				locked = lock.tryLock(3, TimeUnit.SECONDS);
				if (locked) {
					if (!queue.isEmpty()) {
						String name = queue.poll();
						for (Subscriber subscriber : subscribers) {
							subscriber.doIt(name);
						}
					} else {
						System.out.println("await");
						condition.await();
					}
				}
			} catch (InterruptedException e) {
				Thread.interrupted();
				throw new RuntimeException(e);
			} finally {
				if (locked) {
					lock.unlock();
				}
			}
		}
	}
}
