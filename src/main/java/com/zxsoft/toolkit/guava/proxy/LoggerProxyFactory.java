package com.zxsoft.toolkit.guava.proxy;

import com.google.common.reflect.Reflection;

/**
 * 日志代理类
 * 说明：只要简单地指定一组接口及委托类对象，便能动态地获得代理类。
 * 代理类会负责将所有的方法调用分派到委托对象上反射执行，
 * 在分派执行的过程中，开发人员还可以按需调整委托类对象及其功能，
 * @author fgq
 *
 */
public class LoggerProxyFactory {

	public static <T> T getGuavaProxy(Class<T> cla, final T obj) {

		return Reflection.newProxy(cla, new LoggingInvocationHandler<T>(obj));
	}

}
