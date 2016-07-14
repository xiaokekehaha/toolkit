package com.zxsoft.tooklit.guava.proxy;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.zxsoft.toolkit.guava.proxy.LoggerProxyFactory;

public class LoggerProxyFactoryTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testLoggerProxy() {
		Set<String> s = LoggerProxyFactory.getGuavaProxy(Set.class, new HashSet<String>());
		s.add("three");
		if (!s.contains("four")) {
			s.add("four");
		}
		Assert.assertEquals(2, s.size());
		Assert.assertTrue("s不是代理类", Proxy.isProxyClass(s.getClass()));
	}

}
