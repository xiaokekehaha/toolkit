package com.zxsoft.toolkit.concurrent.threadpool;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolExecutorUtils {

	private static Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorUtils.class);

	public static ThreadPoolExecutor createExecutor(int corePoolThread) {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolThread, corePoolThread, 0L,
				TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadFactory(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

					@Override
					public void uncaughtException(Thread t, Throwable e) {
						e.printStackTrace();
						logger.error("create thread exception:" + t.getName());
						executor.shutdown();
					}

				});
				return thread;
			}

		});
		return executor;
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor executor = ThreadPoolExecutorUtils.createExecutor(64);
		executor.execute(new Thread());
		executor.shutdown();
		while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
			logger.info("still waiting executor terminated");
		}

	}
}
