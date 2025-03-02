package top.sorie.juc.executor.my;

public class Subscriber {
	private String myName;

	public Subscriber(String myName) {
		this.myName = myName;
	}

	public void doIt(String name) {
		System.out.println("Hello " + name + ",I'm " + myName);
	}
}
