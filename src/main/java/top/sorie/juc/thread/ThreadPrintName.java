package top.sorie.juc.thread;

public class ThreadPrintName {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new MyThread().start();
		}
	}

	public static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
}
