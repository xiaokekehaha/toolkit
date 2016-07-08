package com.zxsoft.tookit.guava.proxy;

import java.util.HashSet;
import java.util.Set;

import com.google.common.reflect.Reflection;

/**
 * 日志代理类
 * @author fgq
 *
 */
public class LoggerProxyFactory {

	public static <T> T getGuavaProxy(Class<T> cla, final T obj) {

		return Reflection.newProxy(cla, new LoggingInvocationHandler<T>(obj));
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Set<String> s = LoggerProxyFactory.getGuavaProxy(Set.class, new HashSet<String>());
		s.add("three");
		if (!s.contains("four")) {
			s.add("four");
		}
	}
}
