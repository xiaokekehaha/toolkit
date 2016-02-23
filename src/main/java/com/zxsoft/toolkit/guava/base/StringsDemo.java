package com.zxsoft.toolkit.guava.base;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringsDemo {

	public static String combine() {
		Joiner joiner = Joiner.on(";").skipNulls();
		String result = joiner.join("a", "b", "c");
		return result;
	}

	public static int split() {
		Splitter spliter = Splitter.on(";").omitEmptyStrings().trimResults();
		List<String> results = spliter.splitToList("a;s;d");
		return results.size();
	}

	public static void main(String[] args) {
		System.out.println(combine());
		System.out.println(split());
	}
}
