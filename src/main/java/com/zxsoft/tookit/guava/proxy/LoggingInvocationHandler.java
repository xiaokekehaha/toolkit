package com.zxsoft.tookit.guava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用句柄的实现类：日志调用句柄
 * @author fgq
 *
 * @param <T>
 */
public class LoggingInvocationHandler<T> implements InvocationHandler {

	final T underlying;

	public LoggingInvocationHandler(T underlying) {
		this.underlying = underlying;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append(method.getName()).append("(");
		for (int i = 0; args != null && i < args.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(args[i]);
		}
		sb.append(")");
		Object ret = method.invoke(underlying, args);
		if (ret != null) {
			sb.append("->").append(ret);
		}
		System.out.println(sb);
		return ret;
	}

}
