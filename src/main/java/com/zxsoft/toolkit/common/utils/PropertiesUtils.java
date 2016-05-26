package com.zxsoft.toolkit.common.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	public static Properties getProps(String confFileName) {
		Properties result = new Properties();
		logger.info("Load resource: " + confFileName);
		try (InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(confFileName);) {
			result.load(in);
			return result;
		} catch (Exception e) {
			logger.error("Exception:{}", LoggerUtils.transException(e));
			throw new RuntimeException(e);
		}
	}

}
