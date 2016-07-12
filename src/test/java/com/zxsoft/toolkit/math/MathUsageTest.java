package com.zxsoft.toolkit.math;

import org.junit.Assert;
import org.junit.Test;

public class MathUsageTest {

	@Test
	public void testRandomProduce() {
		RandomUsage usage = new RandomUsage();
		int r = usage.getRandomBetweenAB(3, 8);
		Assert.assertTrue(r >= 3 && r <= 8);
	}
}
