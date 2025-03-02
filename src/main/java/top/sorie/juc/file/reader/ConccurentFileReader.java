package top.sorie.juc.file.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ConccurentFileReader {
	public static final ConcurrentHashMap<Character, Integer> CNT = new ConcurrentHashMap<>();

	public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
			2, 5, 2, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10),
			new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true);  // 设置为守护线程
					return thread;
				}
			},
			new ThreadPoolExecutor.AbortPolicy()
	);
	public static Future cntChar(String filePath){
		return EXECUTOR.submit(() -> {
			InputStream resourceAsStream = ConccurentFileReader.class.getClassLoader().getResourceAsStream(filePath);
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					for (int i = 0; i < line.length(); i++) {
						char c = line.charAt(i);
						CNT.merge(c, 1, Integer::sum);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

	}
}
