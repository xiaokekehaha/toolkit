package com.zxsoft.toolkit.cache.redisv2;

import java.util.List;

public interface Cache {

	public boolean exists(String key);

	public long delete(List<String> keys);

	public String add(String key, Object value);

}
