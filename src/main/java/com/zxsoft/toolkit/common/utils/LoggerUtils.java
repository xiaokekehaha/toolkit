package com.zxsoft.toolkit.common.utils;

public class LoggerUtils {

	public static String transException(Exception e) {
		String result = e.getMessage() + ";";
		for (StackTraceElement stack : e.getStackTrace()) {
			result += stack.toString() + ";";
		}

		return result.substring(0, result.length() - 1).replaceAll("\n", ",");
	}
}
