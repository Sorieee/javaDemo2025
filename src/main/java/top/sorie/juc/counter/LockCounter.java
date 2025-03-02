package top.sorie.juc.counter;

import java.util.concurrent.locks.ReentrantLock;

public class LockCounter {
	private volatile Integer cnt;

	private ReentrantLock lock;

	public LockCounter(Integer cnt) {
		this.cnt = cnt;
		this.lock = new ReentrantLock();
	}

	/**
	 * 通过同步块实现线程安全
	 */
	public void increment(){
		boolean locked = false;
		try {
			locked = this.lock.tryLock();
			if (locked) {
				this.cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (locked) {
				this.lock.unlock();
			}
		}
	}

	public Integer getCnt() {
		return cnt;
	}
}
