package com.zxsoft.toolkit.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Foo {

	// SimpleDateFormat is not thread-safe, so give one to each thread
	private static ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-yy-dd:HH:MM:SS");
		}
	};

	public static String format(Date date) {
		return formatter.get().format(date);
	}
}
