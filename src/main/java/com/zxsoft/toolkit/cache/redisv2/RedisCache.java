package com.zxsoft.toolkit.cache.redisv2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCache implements Cache {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private RedisOperation operation;
	private Class<?> cla;
	private String[] fieldsName;

	public RedisCache(Class<?> cla) {
		operation = RedisOperation.getInstance();
		this.cla = cla;
		fieldsName = this.getFieldsName();
	}

	@Override
	public boolean exists(String key) {
		return operation.exists(key);
	}

	@Override
	public long delete(List<String> keys) {
		long result = operation.delete(keys, fieldsName);
		logger.info("delete count = " + result);
		return result;
	}

	@Override
	public String add(String key, Object obj) {
		String result = null;
		if (obj.getClass().equals(cla)) {
			Map<String, String> hashmap = getFieldValueMap(obj);
			result = operation.add(key, hashmap);
		}
		return result;
	}

	private Map<String, String> getFieldValueMap(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, String> values = new HashMap<>();
		try {
			for (Field f : fields) {
				if (Modifier.isPrivate(f.getModifiers())) {
					f.setAccessible(true);
					Object o = f.get(obj);
					if (o != null) {
						values.put(f.getName(), o.toString());
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return values;
	}

	private String[] getFieldsName() {
		List<String> fieldsNameTemp = new ArrayList<>();
		Field[] fields = cla.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isPrivate(f.getModifiers())) {
				f.setAccessible(true);
				fieldsNameTemp.add(f.getName());
			}
		}
		return fieldsNameTemp.toArray(new String[0]);
	}

}
