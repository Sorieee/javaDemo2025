package top.sorie.juc.aqs.counter;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AqsCounter {
	private final Sync sync;
	public AqsCounter(int cnt) {
		this.sync = new Sync(cnt);
	}
	public int get() {
		return this.sync.getCount();
	}
	public int incrementAndGet() {
		return this.sync.incrementAndGet();
	}
	protected int getAndIncrement() {
		return this.sync.getAndIncrement();
	}

	private static final class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 7790963418640640883L;

		Sync(int count) {
			setState(count);
		}

		int getCount() {
			return getState();
		}

		protected int incrementAndGet() {
			// Decrement count; signal when transition to zero
			for (;;) {
				int c = getState();
				int nextc = c + 1;
				if (compareAndSetState(c, nextc))
					return nextc;
			}
		}
		protected int getAndIncrement() {
			// Decrement count; signal when transition to zero
			for (;;) {
				int c = getState();
				int nextc = c + 1;
				if (compareAndSetState(c, nextc))
					return c;
			}
		}
	}

}
