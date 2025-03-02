package top.sorie.juc.automic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
	private AtomicInteger counter;

	public AtomicCounter(){
		counter = new AtomicInteger(0);
	}
	public void cnt() {
		counter.getAndIncrement();
	}
	public int get() {
		return counter.get();
	}
	public static void main(String[] args) throws InterruptedException {
		AtomicCounter atomicCounter = new AtomicCounter();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 200,
				1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
		for (int i = 0; i < 100; i++) {
			int x = i;
			threadPoolExecutor.execute(() -> {
				try {
					System.out.println("就位: " + x);
					cyclicBarrier.await();
					atomicCounter.cnt();
					System.out.println("cnt " + atomicCounter.get());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (BrokenBarrierException e) {
					throw new RuntimeException(e);
				}
			});
		}
		Thread.sleep(1000);
		assert atomicCounter.get() == 100;
		System.out.println("cnt pass");
		threadPoolExecutor.shutdownNow();
	}
}
