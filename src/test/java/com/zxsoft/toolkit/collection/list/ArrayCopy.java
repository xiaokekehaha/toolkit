package com.zxsoft.toolkit.collection.list;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayCopy {

	Integer[] array = new Integer[3];

	@Before
	public void setUp() throws Exception {
		array[0] = 0;
		array[1] = 1;
		array[2] = 2;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArraysCopyOf() {
		Assert.assertEquals(4, Arrays.copyOf(array, 4).length);
		Assert.assertEquals(2, Arrays.copyOf(array, 2).length);
		Assert.assertEquals((Integer) 2, Arrays.copyOf(array, 4)[2]);
		Assert.assertEquals(null, Arrays.copyOf(array, 4)[3]);
	}

}
