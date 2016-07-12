package com.zxsoft.toolkit.math;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUsage {

	//产生[a,b]之间的随机数其通式
	public int getRandomBetweenAB(int a, int b) {
		int result = 0;
		if (b >= a) {
			result = (int) ((b - a + 1) * Math.random() + a);
		}
		return result;
	}

	/**
	 * 多线程程序中产生随机数
	 * @param randomNumberOrigin
	 * @param randomNumberBound
	 * @return
	 */
	public int getConcurrentRandom(int randomNumberOrigin, int randomNumberBound) {
		int result = ThreadLocalRandom.current().nextInt(randomNumberOrigin, randomNumberBound);
		return result;
	}
}
