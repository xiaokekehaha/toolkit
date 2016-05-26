package com.zxsoft.toolkit.cache.redis;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

public class CommandThread implements Runnable {

	private String key = "test_multi_thread";
	public int number;

	public CommandThread(int number) {
		this.number = number;
	}

	@Override
	public void run() {

		try (Jedis jedis = RedisConnector.getJedis();) {
			jedis.sadd(key, String.valueOf(number));
			System.out.println(number + "sleep start");
			TimeUnit.SECONDS.sleep(3);
			System.out.println(number + "sleep end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
