package com.zxsoft.toolkit.concurrent.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

/*
 * thread local示例
 */
public class ThreadId {

	private static final ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		};
	};

	private int a;

	public int getSimpleNextId() {
		a = a + 1;
		return a;
	}

	private static int b;

	public int getStaticNextId() {
		b = b + 1;
		return b;
	}

	public int getNextId() {
		threadId.set(threadId.get() + 1);
		return threadId.get();
	}

	private static class Test implements Runnable {

		private ThreadId id;

		public Test(ThreadId id) {
			this.id = id;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("thread local:" + Thread.currentThread().getName() + ";" + id.getNextId()
						+ id.getNextId() + id.getNextId());
				System.out.println("local:" + Thread.currentThread().getName() + ";" + id.getSimpleNextId()
						+ id.getSimpleNextId() + id.getSimpleNextId());
				System.out.println("static:" + Thread.currentThread().getName() + ";" + id.getStaticNextId()
						+ id.getStaticNextId() + id.getStaticNextId());
			}
		}
	}

	public static void main(String[] args) {
		ThreadId id = new ThreadId();
		ThreadPoolExecutor executor = ThreadPoolExecutorUtils.createExecutor(4);
		for (int i = 1; i < 4; i++) {
			executor.execute(new Test(id));
		}
		executor.shutdown();
	}

}
