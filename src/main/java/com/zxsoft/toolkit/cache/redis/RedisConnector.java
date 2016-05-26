package com.zxsoft.toolkit.cache.redis;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.zxsoft.toolkit.common.utils.PropertiesUtils;
import com.zxsoft.toolkit.concurrent.threadpool.ThreadPoolExecutorUtils;

public class RedisConnector {

	public static Logger logger = LoggerFactory.getLogger(RedisConnector.class);
	private static volatile JedisPool jedisPoolInstance;

	public static JedisPool getJedisPoolInstance() {
		JedisPool result = jedisPoolInstance;
		if (result == null) {
			synchronized (RedisConnector.class) {
				result = jedisPoolInstance;
				if (result == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setTestOnBorrow(true);
					config.setTestOnReturn(true);
					config.setMaxWaitMillis(10000);//设置排队等待的最长时间
					config.setMaxTotal(3);
					Properties props = PropertiesUtils.getProps("redis.properties");
					jedisPoolInstance = result = new JedisPool(config, props.getProperty("redis.host"),
							Integer.parseInt(props.getProperty("redis.port")), Integer.parseInt(props
									.getProperty("redis.timeout")), props.getProperty("redis.password"));
				}
			}
		}
		return result;
	}

	public static Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = getJedisPoolInstance().getResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jedis;
	}

	public static void closePool() {
		while (jedisPoolInstance.getNumActive() > 0) {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jedisPoolInstance.destroy();
		System.out.println("destory success");
	}

	public static void main(String[] args) throws InterruptedException {
		MonitorJedisPool monitor = new MonitorJedisPool(RedisConnector.getJedisPoolInstance());
		ExecutorService service = ThreadPoolExecutorUtils.createExecutor(3);
		Thread t = new Thread(monitor);
		t.start();
		for (int i = 0; i < 10; i++) {
			CommandThread command = new CommandThread(i);
			service.execute(command);
			System.out.println(i + "thread　added");
			TimeUnit.SECONDS.sleep(1);
		}
		RedisConnector.closePool();
		monitor.shutDown();
		service.shutdown();
	}
}
