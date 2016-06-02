package com.zxsoft.toolkit.math;

public class MathUsage {

	//产生[a,b]之间的随机数其通式
	public int getRandomBetweenAB(int a, int b) {
		int result = 0;
		if (b >= a) {
			result = (int) ((b - a + 1) * Math.random() + a);
		}
		return result;
	}

	public static void main(String[] args) {
		MathUsage usage = new MathUsage();
		int r = usage.getRandomBetweenAB(3, 8);
		System.out.println(r);
	}
}
