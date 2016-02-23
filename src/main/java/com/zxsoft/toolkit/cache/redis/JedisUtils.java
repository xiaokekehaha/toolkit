package com.zxsoft.toolkit.cache.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.zxsoft.toolkit.common.utils.HostAndPort;
import com.zxsoft.toolkit.common.utils.LoggerUtils;

public class JedisUtils {

	public static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
	private static Map<String, JedisPool> jedisPoolsMap = new HashMap<>();
	public static int TIMEOUT = 20;

	public static JedisPool getPool(HostAndPort hostAndPort) {
		String key = hostAndPort.toString();
		JedisPool pool = null;
		if (!jedisPoolsMap.containsKey(key)) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(20);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setMinIdle(10);
			config.setMaxWaitMillis(60000);
			config.setMaxTotal(20);
			try {
				pool = new JedisPool(config, hostAndPort.getHost(), hostAndPort.getPort(), TIMEOUT);
			} catch (Exception e) {
				logger.error(LoggerUtils.transException(e));
			}
		} else {
			pool = jedisPoolsMap.get(key);
		}
		return pool;
	}

	public static Jedis getJedis(HostAndPort hostAndPort) {
		Jedis jedis = null;
		try {
			jedis = getPool(hostAndPort).getResource();
		} catch (Exception e) {
			logger.error(LoggerUtils.transException(e));
		} finally {
			jedis.close();
		}
		return jedis;
	}

	public static void main(String[] args) {
		HostAndPort hp = new HostAndPort("192.168.6.126", 6379);
		MonitorJedisPool monitor = new MonitorJedisPool(JedisUtils.getPool(hp));
		Thread thread = new Thread(monitor);
		thread.start();
		Map<String, String> china = new HashMap<>();
		china.put("安徽", "123.123,1234.84");
		for (int i = 0; i < 100; i++) {
			Jedis jedis = JedisUtils.getJedis(hp);
			if (jedis != null) {
				//jedis.hmset("geo:china", china);
				System.out.println(jedis.hmget("geo:china", "安徽"));
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jedis.close();
		}
		//monitor.shutDown();
	}

}
