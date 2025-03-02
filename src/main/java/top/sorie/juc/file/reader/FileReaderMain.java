package top.sorie.juc.file.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FileReaderMain {
	public static void main(String[] args) {
		List<Future> futures = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			futures.add(ConccurentFileReader.cntChar("file/file" + (i + 1) + ".txt"));
		}
		for (Future future : futures) {
			try {
				future.get();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		for (Map.Entry<Character, Integer> characterIntegerEntry : ConccurentFileReader.CNT.entrySet()) {
			System.out.println(characterIntegerEntry.getKey() + " " + characterIntegerEntry.getValue());
		}
//		ConccurentFileReader.EXECUTOR.shutdown();
	}
}
