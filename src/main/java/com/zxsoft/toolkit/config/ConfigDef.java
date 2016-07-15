package com.zxsoft.toolkit.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.kafka.common.errors.IllegalSaslStateException;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;

public class ConfigDef {

	private static final Object NO_DEFAULT_VALUE = new String("");

	private final Map<String, ConfigKey> configKeys = new TreeMap<String, ConfigKey>();

	public Set<String> names() {
		return Collections.unmodifiableSet(configKeys.keySet());
	}

	public ConfigDef define(String name, Type type, Object defaultValue, Validator validator, Importance importance,
			String documentation) {
		if (configKeys.containsKey(name)) {
			throw new ConfigException("该配置项 " + name + " 已经定义了");
		}
		Object parsedDefault = defaultValue == NO_DEFAULT_VALUE ? NO_DEFAULT_VALUE
				: parseType(name, defaultValue, type);
		configKeys.put(name, new ConfigKey(name, type, parsedDefault, validator, importance, documentation));
		return this;
	}

	public ConfigDef defineOverride(String name, Type type, Object defaultValue, Validator validator,
			Importance importance, String documentation) {
		if (!configKeys.containsKey(name)) {
			throw new ConfigException("该配置项 " + name + " 定义为覆盖原始配置，但是该配置项不存在");
		}
		Object parsedDefault = defaultValue == NO_DEFAULT_VALUE ? NO_DEFAULT_VALUE
				: parseType(name, defaultValue, type);
		configKeys.put(name, new ConfigKey(name, type, parsedDefault, validator, importance, documentation));
		return this;
	}

	public ConfigDef define(String name, Type type, Object defaultValue, Importance importance, String documentation) {
		return define(name, type, defaultValue, null, importance, documentation);
	}

	public ConfigDef defineOverride(String name, Type type, Object defaultValue, Importance importance,
			String documentation) {
		return defineOverride(name, type, defaultValue, null, importance, documentation);
	}

	public ConfigDef define(String name, Type type, Validator validator, Importance importance, String documentation) {
		return define(name, type, NO_DEFAULT_VALUE, validator, importance, documentation);
	}

	public ConfigDef defineOverride(String name, Type type, Validator validator, Importance importance,
			String documentation) {
		return defineOverride(name, type, NO_DEFAULT_VALUE, validator, importance, documentation);
	}

	public ConfigDef define(String name, Type type, Importance importance, String documentation) {
		return define(name, type, NO_DEFAULT_VALUE, null, importance, documentation);
	}

	public ConfigDef defineOverride(String name, Type type, Importance importance, String documentation) {
		return defineOverride(name, type, NO_DEFAULT_VALUE, null, importance, documentation);
	}

	public Map<String, Object> parse(Map<?, ?> props) {
		Map<String, Object> values = new HashMap<String, Object>();
		for (ConfigKey key : configKeys.values()) {
			Object value;
			if (props.containsKey(key.name)) {
				value = parseType(key.name, props.get(key.name), key.type);
			} else if (key.defaultValue == NO_DEFAULT_VALUE) {
				throw new ConfigException("需要的配置项丢失" + key.name + "没有默认值");
			} else {
				value = key.defaultValue;
			}
			if (key.validator != null) {
				key.validator.ensureValid(key.name, value);
			}
			values.put(key.name, value);
		}
		return values;
	}

	private Object parseType(String name, Object value, Type type) {
		try {
			String trimmed = null;
			if (value instanceof String) {
				trimmed = ((String) value).trim();
			}

			switch (type) {
			case BOOLEAN:
				if (value instanceof String) {
					if (trimmed.equalsIgnoreCase("true")) {
						return true;
					} else if (trimmed.equalsIgnoreCase("false")) {
						return false;
					} else {
						throw new ConfigException(name, value, "期望的类型不是true也不是false");
					}
				} else if (value instanceof Boolean) {
					return value;
				} else {
					throw new ConfigException(name, value, "期望的类型不是true也不是false");
				}
			case STRING:
				if (value instanceof String) {
					return trimmed;
				} else {
					throw new ConfigException(name, value, "期望的值是字符串,但是它的类型是" + value.getClass().getName());
				}
			case INT:
				if (value instanceof Integer) {
					return value;
				} else if (value instanceof String) {
					return Integer.parseInt(trimmed);
				} else {
					throw new ConfigException(name, value, "期望类型是数值类型");
				}
			case DOUBLE:
				if (value instanceof Number) {
					return ((Number) value).doubleValue();
				} else if (value instanceof String) {
					return Double.parseDouble(trimmed);
				} else {
					throw new ConfigException(name, value, "期望类型是数值类型.");
				}
			case LIST:
				if (value instanceof List) {
					return value;
				} else if (value instanceof String) {
					if (trimmed.isEmpty()) {
						return Collections.emptyList();
					} else {
						return Arrays.asList(trimmed.split("\\s*,\\s*", -1));
					}
				} else {
					throw new ConfigException(name, value, "期望的类型是以,分割的list");
				}
			case CLASS:
				if (value instanceof Class) {
					return value;
				} else if (value instanceof String) {
					return Class.forName(trimmed);
				} else {
					throw new ConfigException(name, value, "期望是Class类型");
				}
			default:
				throw new IllegalSaslStateException("不知道的数据类型");
			}
		} catch (NumberFormatException e) {
			throw new ConfigException(name, value, "不是数值类型");
		} catch (ClassNotFoundException e) {
			throw new ConfigException(name, value, "Class" + value + "不存在");
		}
	}

	private static class ConfigKey implements Comparable<ConfigKey> {
		public final String name;
		public final Type type;
		public final String documentation;
		public final Object defaultValue;
		public final Validator validator;
		public final Importance importance;

		public ConfigKey(String name, Type type, Object defaultValue, Validator validator, Importance importance,
				String documentation) {
			super();
			this.name = name;
			this.type = type;
			this.defaultValue = defaultValue;
			this.validator = validator;
			if (this.validator != null) {
				this.validator.ensureValid(name, defaultValue);
			}
			this.importance = importance;
			this.documentation = documentation;
		}

		public boolean hasDefault() {
			return this.defaultValue != NO_DEFAULT_VALUE;
		}

		@Override
		public int compareTo(ConfigKey o) {
			if (!this.hasDefault() && o.hasDefault()) {
				return -1;
			} else if (!o.hasDefault() && this.hasDefault()) {
				return 1;
			} else {
				return ComparisonChain.start().compare(this.importance, o.importance).compare(this.name, o.name)
						.result();
			}
		}
	}

	public enum Type {
		BOOLEAN, STRING, INT, LONG, DOUBLE, LIST, CLASS;
	}

	public enum Importance {
		HIGH, MEDIUM, LOW
	}

	public interface Validator {
		public void ensureValid(String name, Object o);
	}

	public static class Range implements Validator {
		private final Number min;
		private final Number max;

		private Range(Number min, Number max) {
			this.min = min;
			this.max = max;
		}

		public static Range atLeast(Number min) {
			return new Range(min, null);
		}

		public static Range between(Number min, Number max) {
			return new Range(min, max);
		}

		@Override
		public void ensureValid(String name, Object o) {
			Number n = (Number) o;
			if (min != null && n.doubleValue() < min.doubleValue()) {
				throw new ConfigException(name, o, "值应当大于最小值" + min.doubleValue());
			}
			if (max != null && n.doubleValue() > max.doubleValue()) {
				throw new ConfigException(name, o, "值应当小于最大值" + max.doubleValue());
			}
		}
	}

	public static class ValidString implements Validator {
		List<String> validStrings;

		private ValidString(List<String> validStrings) {
			this.validStrings = validStrings;
		}

		public static ValidString in(List<String> validStrings) {
			return new ValidString(validStrings);
		}

		@Override
		public void ensureValid(String name, Object o) {
			String s = (String) o;
			if (!validStrings.contains(s)) {
				throw new ConfigException(name, o, "字符串要属于以下的字符串列表中" + Joiner.on(",").join(validStrings));
			}
		}

	}
}
