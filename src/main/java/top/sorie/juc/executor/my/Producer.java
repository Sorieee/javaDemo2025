package top.sorie.juc.executor.my;

public class Producer {
	public static void main(String[] args) throws InterruptedException {
		Consumer consumer = new Consumer(10);
		consumer.start();
		consumer.subscribe(new Subscriber("sorie"));
		consumer.event("David");
		Thread.sleep(1000);
		consumer.event("Liming");
		System.out.println("1");
	}
}
