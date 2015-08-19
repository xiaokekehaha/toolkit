package com.zxsoft.toolkit.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import zx.soft.utils.json.JsonUtils;

import com.zxsoft.toolkit.httpclient.clientimpl.ClientDaoHttpImpl;
import com.zxsoft.toolkit.httpclient.clientimpl.PostThread;

public class TestExecutor {

	public static void main(String[] args) throws InterruptedException {

		ClientDaoHttpImpl client = new ClientDaoHttpImpl();

		ThreadPoolExecutor executor = ThreadPoolExecutorUtils.createExecutor(64);
		String num = null;
		int threadnum = 10000;
		int postnum = 100;
		MyMonitorThread myMonitorThread = new MyMonitorThread(executor, 20);
		Thread monitor = new Thread(myMonitorThread);
		monitor.setDaemon(true);
		monitor.start();
		for (int i = 0; i < threadnum; i++) {
			List<String> nums = new ArrayList<>();
			for (int j = 0; j < postnum; j++) {
				num = String.valueOf(i) + String.valueOf(j);
				nums.add(num);
			}
			System.out.println("start new thread");
			executor.execute(new PostThread(client, JsonUtils.toJsonWithoutPretty(nums)));
		}

		executor.shutdown();
		while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
			System.out.println("still waiting executor ");
		}
		myMonitorThread.shutdown();
	}
}
