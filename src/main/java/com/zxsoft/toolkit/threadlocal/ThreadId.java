package com.zxsoft.toolkit.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {

	private static AtomicInteger id = new AtomicInteger(0);

	/**
	 * ThreadLocal instances are typically private static fields in classes
	 * that wish to associate state with a thread (e.g., a user ID or Transaction ID).
	 */
	private static ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return id.incrementAndGet();
		}
	};

	//获取每个线程对应的唯一id
	public Integer get() {
		return threadId.get();
	}

}
