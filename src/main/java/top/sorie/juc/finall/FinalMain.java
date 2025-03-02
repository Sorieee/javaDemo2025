package top.sorie.juc.finall;

public class FinalMain {
	private final int x;

	public FinalMain(int x) {
		this.x = x;
	}

	public static void main(String[] args) {
		FinalMain finalMain = new FinalMain(1);
		System.out.println(finalMain);
	}
}
