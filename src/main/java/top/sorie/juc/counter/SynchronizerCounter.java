package top.sorie.juc.counter;

/**
 * 使用
 * @author soriee
 * @date 2025/2/22 1:31
 */
public class SynchronizerCounter {
	public volatile Integer cnt;

	public SynchronizerCounter(Integer cnt) {
		this.cnt = cnt;
	}

	/**
	 * 通过同步块实现线程安全
	 */
	public void increment(){
		synchronized(this){
			cnt++;
		}
	}

	public Integer getCnt() {
		return cnt;
	}
}
