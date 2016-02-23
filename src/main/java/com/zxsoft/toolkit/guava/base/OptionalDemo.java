package com.zxsoft.toolkit.guava.base;

import com.google.common.base.Optional;

public class OptionalDemo {

	public static void main(String[] args) {

		Optional<Integer> possible = Optional.of(6);
		Optional<Integer> absentOpt = Optional.absent();
		Optional<Integer> nullableOpt = Optional.fromNullable(null);
		Optional<Integer> noNullableOpt = Optional.fromNullable(10);

		if (possible.isPresent()) {
			System.out.println(possible.get());
		}
		if (absentOpt.isPresent()) {
			System.out.println(absentOpt.get());
		}
		if (nullableOpt.isPresent()) {
			System.out.println(nullableOpt.get());
		}
		if (noNullableOpt.isPresent()) {
			System.out.println(noNullableOpt.get());
		}

	}
}
