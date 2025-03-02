package top.sorie.juc.aqs.counter;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class AqsCounterMain {
	public static void main(String[] args) throws InterruptedException {
		AqsCounter counter = new AqsCounter(0);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		CountDownLatch countDownLatch = new CountDownLatch(5);
		for (int i = 0; i < 5; i++) {
			executorService.execute(() -> {
				for (int x = 0; x < 10000; x++) {
					counter.incrementAndGet();
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		if (counter.get() == 5 * 10000) {
			log.info("aqs counter testcase pass");
		} else {
			log.info("aqs counter testcase fail");
		}
		executorService.shutdown();
		while (!executorService.isShutdown()) {

		}
	}
}
