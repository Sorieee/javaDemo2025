package top.sorie.juc.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SCache {
	private Map<String, CacheNode> CACHE = new ConcurrentHashMap<>(16);
	public <T> T getValue(String key, Class<T> clazz) {
		CacheNode cacheNode = CACHE.get(key);
		if (cacheNode == null || cacheNode.getVal() == null) {
			return null;
		}
		long cur = System.currentTimeMillis();
		// 惰性删除策略
		if (cacheNode.getExpiredTime() < cur) {
			CACHE.remove(key);
		}
		if (!clazz.isAssignableFrom(cacheNode.getVal().getClass())) {
			throw new RuntimeException("类型不匹配");
		}
		return (T) cacheNode.getVal();
	}

	public <T> void put(String key, T value, long expire) {
		CacheNode<T> node = new CacheNode<>(expire, value);
		CACHE.put(key, node);
	}

	public static class CacheNode<T> {
		private Long expiredTime;
		private T val;

		public CacheNode(Long expiredTime, T val) {
			this.expiredTime = expiredTime;
			this.val = val;
		}

		public Long getExpiredTime() {
			return expiredTime;
		}

		public void setExpiredTime(Long expiredTime) {
			this.expiredTime = expiredTime;
		}

		public T getVal() {
			return val;
		}

		public void setVal(T val) {
			this.val = val;
		}
	}
}
