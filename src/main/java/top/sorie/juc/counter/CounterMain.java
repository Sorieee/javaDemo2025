package top.sorie.juc.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

public class CounterMain {
	public static void main(String[] args) {
		doTest1();
		doTest2();
	}
	public static void doTest1() {
		/**
		 * 同步块
		 */
		SynchronizerCounter synchronizerCounter = new SynchronizerCounter(0);
		int cnt = 10000;
		CountDownLatch countDownLatch = new CountDownLatch(cnt);
		for (int i = 0; i < cnt; i++) {
			new Thread(() -> {
				synchronizerCounter.increment();
				countDownLatch.countDown();
			}).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		assert synchronizerCounter.getCnt().intValue() == cnt;
		System.out.println("synchronizerCounter testcase pass");
	}

	public static void doTest2() {
		/**
		 * 同步块
		 */
		LockCounter counter = new LockCounter(0);
		int cnt = 10000;
		CountDownLatch countDownLatch = new CountDownLatch(cnt);
		for (int i = 0; i < cnt; i++) {
			new Thread(() -> {
				counter.increment();
				countDownLatch.countDown();
			}).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		assert counter.getCnt().intValue() == cnt;
		System.out.println("LockCounter testcase pass");
	}
}
