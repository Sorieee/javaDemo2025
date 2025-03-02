package top.sorie.juc.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureMain {
	public static void main(String[] args) {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			// 模拟耗时操作
			return 42;
		});
		future.thenApply(r -> r + 1)
				.thenAccept(r -> System.out.println("Result: " + r));

	}
}
