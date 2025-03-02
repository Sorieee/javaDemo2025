package top.sorie.juc.singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class Xon {
	private static volatile Xon instance;

	private Xon() {}

	public Xon getInstance() throws InterruptedException {
		if (instance != null) {
			return instance;
		}
		synchronized (Xon.class) {
			if (instance != null) {
				return instance;
			}
			// 模拟复杂的情况
			Thread.sleep(1000);
			instance = new Xon();
			return instance;
		}
	}

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
		new Thread(() -> {
			try {
				cyclicBarrier.await();
				Xon instance1 = instance.getInstance();
				log.info(instance1.toString());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		});
		new Thread(() -> {
			try {
				cyclicBarrier.await();
				Xon instance2 = instance.getInstance();
				log.info(instance2.toString());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
