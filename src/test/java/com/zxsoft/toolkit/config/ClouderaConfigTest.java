package com.zxsoft.toolkit.config;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ClouderaConfigTest {

	@Test
	public void testHbaseConfig() {

		Map<String, Object> originals = new HashMap<>();
		originals.put(ClouderaConfig.HBASE_ZOOKEEPER_QUORUM_CONFIG, "datacenter01");
		originals.put(ClouderaConfig.HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_CONFIG, 2181);
		originals.put("key", "value");
		ClouderaConfig config = new ClouderaConfig(originals);
		System.out.println(config.toString());
		config.get(ClouderaConfig.HBASE_ZOOKEEPER_QUORUM_CONFIG);
		config.get(ClouderaConfig.HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_CONFIG);
		System.out.println(config.unusedToString());
		Assert.assertEquals("datacenter01", config.get(ClouderaConfig.HBASE_ZOOKEEPER_QUORUM_CONFIG));
	}
}
