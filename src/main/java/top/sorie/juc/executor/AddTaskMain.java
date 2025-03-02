package top.sorie.juc.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddTaskMain {
	public static void main(String[] args) {
		List<Integer> num1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> num2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Future<Integer>> futureList = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			Future<Integer> f = executorService.submit(() -> {
				Thread.currentThread().sleep(new Random().nextInt(10) * 1000);
				return num1.get(finalI) + num2.get(finalI);
			});
			futureList.add(f);
		}
		for (Future<Integer> f : futureList) {
			try {
				Integer integer = f.get();
				System.out.println(integer);
			} catch (InterruptedException e) {
				Thread.interrupted();
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}

		}
		executorService.shutdown();
	}
}
