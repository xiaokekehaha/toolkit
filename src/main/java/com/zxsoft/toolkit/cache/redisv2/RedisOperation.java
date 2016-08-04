package com.zxsoft.toolkit.cache.redisv2;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.zxsoft.toolkit.common.utils.PropertiesUtils;

/**
 * redis操作类
 * @author fgq
 *
 */
public class RedisOperation {

	private static Logger logger = LoggerFactory.getLogger(RedisOperation.class);

	private static JedisPool jedisPool;
	private static RedisOperation redisOperation;

	/**
	 * 判断指定key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		boolean result = false;
		try (Jedis jedis = jedisPool.getResource()) {
			result = jedis.exists(key);
		}
		return result;
	}

	/**
	 * 添加hashmap数据
	 * @param key
	 * @param hashmap
	 * @return
	 */
	public String add(String key, Map<String, String> hashmap) {
		String result = null;
		if (hashmap != null && hashmap.size() > 0) {
			try (Jedis jedis = jedisPool.getResource()) {
				result = jedis.hmset(key, hashmap);
			}
			logger.info("add " + key + " : " + result);
		}
		return result;
	}

	/**
	 * 删除keys
	 * @param keys
	 * @param fields
	 * @return
	 */
	public long delete(List<String> keys, String... fields) {
		long deletedCount = 0;
		try (Jedis jedis = jedisPool.getResource()) {
			for (String key : keys) {
				deletedCount = deletedCount + jedis.hdel(key, fields);
				logger.info("delete " + key);
			}
		}
		return deletedCount;
	}

	private RedisOperation() {
		JedisPoolConfig config = new JedisPoolConfig();
		Properties props = PropertiesUtils.getProps("utils.properties");
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setMaxWaitMillis(Integer.parseInt(props.getProperty("redis.maxwaitmillis")));//设置排队等待的最长时间
		config.setMaxTotal(Integer.parseInt(props.getProperty("redis.maxtotal")));
		config.setMaxIdle(Integer.parseInt(props.getProperty("redis.maxidle")));
		jedisPool = new JedisPool(config, props.getProperty("redis.host"), Integer.parseInt(props
				.getProperty("redis.port")), Integer.parseInt(props.getProperty("redis.timeout")));
	}

	public static RedisOperation getInstance() {
		if (redisOperation == null) {
			redisOperation = new RedisOperation();
		}
		return redisOperation;
	}

	public static void closePool() {
		while (jedisPool.getNumActive() > 0) {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jedisPool.destroy();
		logger.info("destory success");
	}

}
