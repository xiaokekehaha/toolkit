package com.zxsoft.toolkit.cache.redis;

import redis.clients.jedis.JedisPool;

public class MonitorJedisPool implements Runnable {

	private JedisPool pool;
	private boolean run = true;

	public MonitorJedisPool(JedisPool jedisPool) {
		this.pool = jedisPool;
	};

	@Override
	public void run() {
		while (run) {
			System.out.println("active:" + pool.getNumActive() + "; idle:" + pool.getNumIdle() + "; waiters:"
					+ pool.getNumWaiters());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutDown() {
		run = false;
	}
}
