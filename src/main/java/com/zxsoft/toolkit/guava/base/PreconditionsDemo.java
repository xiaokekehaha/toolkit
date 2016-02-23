package com.zxsoft.toolkit.guava.base;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class PreconditionsDemo {

	public static void getPersonByPrecondition(int age, String name) {
		Preconditions.checkNotNull(name, "name is null");
		Preconditions.checkArgument(name.length() > 0, "name length is zero");
		Preconditions.checkArgument(age > 0, "age　必须大于0");
		System.out.println(age + ";" + name);
	}

	public static void main(String[] args) {

		getPersonByPrecondition(8, "hello");

		try {
			getPersonByPrecondition(-8, "hello");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			getPersonByPrecondition(8, "");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		try {
			getPersonByPrecondition(8, null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		List<Integer> intList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			intList.add(i);
		}

		try {
			System.out.println(Preconditions.checkElementIndex(6, intList.size()));
			System.out.println(Preconditions.checkPositionIndex(1, intList.size()));
			Preconditions.checkPositionIndexes(4, 3, intList.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
