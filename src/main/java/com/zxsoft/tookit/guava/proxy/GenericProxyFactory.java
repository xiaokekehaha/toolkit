package com.zxsoft.tookit.guava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.google.common.reflect.Reflection;

/**
 * 通用代理工厂类
 * @author fgq
 *
 */
public class GenericProxyFactory {

	public static <T> T getGuavaProxy(Class<T> cla, final T obj) {

		T t = Reflection.newProxy(cla, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				return method.invoke(obj, args);

			}
		});
		return t;
	}

	public static <T> T getProxy(Class<T> intf, final T obj) {
		@SuppressWarnings("unchecked")
		T t = (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf },
				new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return method.invoke(obj, args);
			}

		});

		return t;
	}
}
