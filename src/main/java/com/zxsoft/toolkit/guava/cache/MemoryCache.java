package com.zxsoft.toolkit.guava.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class MemoryCache<K, V> {

	private static Logger logger = LoggerFactory.getLogger(MemoryCache.class);
	private Cache<K, V> cache;

	public MemoryCache() {
		Cache<K, V> cache = CacheBuilder.newBuilder().concurrencyLevel(4).removalListener(new RemovalListener<K, V>() {

			@Override
			public void onRemoval(RemovalNotification<K, V> notification) {
				logger.info(notification.getKey() + "was removed,{" + notification.getValue() + "},and caused "
						+ notification.getCause());
				//考虑从数据库中清除该条记录
			}

		}).build();
		this.cache = cache;
	}

	public V getIfPresent(K key) {
		return cache.getIfPresent(key);
	}

	public void put(K key, V value) {
		cache.put(key, value);
	}

	public boolean exists(K key) {
		return cache.getIfPresent(key) == null ? false : true;
	}

	public void invalidate(K key) {
		cache.invalidate(key);
	}

	public long size() {
		return cache.size();
	}

	public V get(K key, Callable<? extends V> callable) {
		V value = null;
		try {
			value = cache.get(key, callable);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return value;
	}

	public V get(K key) throws ExecutionException {
		return cache.get(key, new Callable<V>() {
			@Override
			public V call() throws Exception {
				return null;
			}
		});
	}

	public static void main(String[] args) throws ExecutionException {
		MemoryCache<String, String> cache = new MemoryCache<>();
		String value = cache.getIfPresent("hello");
		if (value != null) {
			System.out.println(value);
		} else {
			cache.put("hello", "smile");
			System.out.println(cache.getIfPresent("hello"));
		}
		try {
			cache.get("llo");
		} catch (InvalidCacheLoadException e) {
			//捕获invalidCacheLoadException
			cache.put("llo", "test");
		}
	}
}
