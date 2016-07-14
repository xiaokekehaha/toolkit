package com.zxsoft.toolkit.guava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JavaBeanInvocationHandler implements InvocationHandler {

	Map<String, Object> properties = new HashMap<String, Object>();

	public JavaBeanInvocationHandler(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String meth = method.getName();
		if (meth.startsWith("get")) {
			String prop = meth.substring(3);
			Object o = properties.get(prop);
			if (o != null && !method.getReturnType().isInstance(o))
				throw new ClassCastException();
			return o;
		} else if (meth.startsWith("set")) {
			String prop = meth.substring(3);
			properties.put(prop, args);
			return null;
		} else {
			return method.invoke(properties, args);
		}
	}
}
