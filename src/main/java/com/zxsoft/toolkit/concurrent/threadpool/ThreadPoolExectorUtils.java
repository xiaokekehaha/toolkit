package com.zxsoft.toolkit.concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExectorUtils {

	private static ThreadPoolExecutor executor;

	public static ThreadPoolExecutor createExecutor(int corePoolThread, int maximumPoolSize) {
		executor = new ThreadPoolExecutor(corePoolThread, maximumPoolSize, 0L, TimeUnit.MICROSECONDS,
				new ArrayBlockingQueue<Runnable>(corePoolThread * 2), new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	public static ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public static void executeTask(Runnable thread) {

		if (!executor.isShutdown()) {
			executor.execute(thread);
		}

	}

	public static void close() {
		try {
			executor.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
