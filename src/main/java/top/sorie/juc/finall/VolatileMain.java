package top.sorie.juc.finall;

public class VolatileMain {
	private volatile int x;

	public VolatileMain(int x) {
		this.x = x;
	}

	public static void main(String[] args) {
		FinalMain finalMain = new FinalMain(1);
		System.out.println(finalMain);
	}
}
