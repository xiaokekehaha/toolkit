package com.zxsoft.toolkit.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractConfig {

	private final Logger log = LoggerFactory.getLogger(AbstractConfig.class);

	/* set保存已经使用的配置，并用来检测未使用的配置*/
	private final Set<String> used;

	/* 使用者传入的原始值*/
	private final Map<String, ?> originals;

	/* 解析的值*/
	private final Map<String, Object> values;

	@SuppressWarnings("unchecked")
	public AbstractConfig(ConfigDef definition, Map<?, ?> originals) {

		for (Object key : originals.keySet()) {
			if (!(key instanceof String)) {
				throw new ConfigException(key.toString(), originals.get(key), "key必须是一个字符串");
			}
		}

		this.originals = (Map<String, ?>) originals;
		this.values = definition.parse(this.originals);
		this.used = Collections.synchronizedSet(new HashSet<String>());
	}

	protected Object get(String key) {
		if (!values.containsKey(key)) {
			throw new ConfigException(String.format("不知道的配置项'%s'", key));
		}
		used.add(key);
		return values.get(key);
	}

	public int getInt(String key) {
		return (Integer) get(key);
	}

	public long getLong(String key) {
		return (Long) get(key);
	}

	public double getDouble(String key) {
		return (Double) get(key);
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(String key) {
		return (List<String>) get(key);
	}

	public boolean getBoolean(String key) {
		return (Boolean) get(key);
	}

	public String getString(String key) {
		return (String) get(key);
	}

	public Class<?> getClass(String key) {
		return (Class<?>) get(key);
	}

	public Set<String> unused() {
		Set<String> keys = new HashSet<String>(originals.keySet());
		keys.removeAll(used);
		return keys;
	}

	public String unusedToString() {
		StringBuilder sb = new StringBuilder();
		if (unused().size() > 0) {
			sb.append(String.format("提供了配置，但是未使用该配置项."));
			sb.append('\n');
			for (String key : unused()) {
				sb.append('\t');
				sb.append(key);
				sb.append(" = ");
				sb.append(this.originals.get(key));
				sb.append('\n');
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(" values: ");
		b.append("\n");
		for (Map.Entry<String, Object> entry : this.values.entrySet()) {
			b.append('\t');
			b.append(entry.getKey());
			b.append(" = ");
			b.append(entry.getValue());
			b.append("\n");
		}
		return b.toString();
	}

}
