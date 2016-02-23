package com.zxsoft.toolkit.cache.redis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.zxsoft.toolkit.common.utils.HostAndPort;

public class JedisUtilsTest {

	@Test
	public void test() {

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
