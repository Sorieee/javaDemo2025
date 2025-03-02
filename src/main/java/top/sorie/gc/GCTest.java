package top.sorie.gc;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
	public static void test() {
		List<byte[]> list = new ArrayList<>();
		int x = 0;
		int t = 12800;
		while (t-- > 0) {
			list.add(new byte[1024 * 1024]); // 每次分配 1MB
			if (list.size() > 100) { // 避免无限增长
				list.clear();
			}
		}
	}
}
