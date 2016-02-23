package com.zxsoft.toolkit.guava.base;

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public class HashDemo {

	public static void main(String[] args) {
		String source = "hello";
		HashCode code = Hashing.md5().hashString(source, Charset.forName("UTF-8"));
		System.out.println(code.toString());

	}
}
