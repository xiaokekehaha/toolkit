package com.zxsoft.toolkit.guava.base;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class ImmutableSetDemo {

	private static final ImmutableSet<String> COLORS = ImmutableSet.of("RED", "ORANGE");

	public static void main(String[] args) {
		Set<String> sets = new HashSet<>();
		sets.add("WHITE");
		Foo foo = new Foo(sets);
		foo.printset();
		System.out.println(COLORS.asList());
		System.out.println(ImmutableSet.<String> builder().addAll(COLORS).add("BLACK").build().asList());
	}

	public static class Foo {
		private ImmutableSet<String> colors;

		public Foo(Set<String> bar) {
			this.colors = ImmutableSet.copyOf(bar);
		}

		public void printset() {
			System.out.println(colors.asList().get(0));
		}
	}
}
