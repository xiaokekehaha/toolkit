package com.zxsoft.toolkit.config;

import java.util.Map;

import com.zxsoft.toolkit.config.ConfigDef.Importance;
import com.zxsoft.toolkit.config.ConfigDef.Type;

public class ClouderaConfig extends AbstractConfig {

	public static final String HBASE_ZOOKEEPER_QUORUM_CONFIG = "hbase.zookeeper.quorum";
	private static final String HBASE_ZOOKEEPER_QUORUM_DOC = "HBase所在集群的zookeeper机器ip";

	public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_CONFIG = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_DOC = "HBase所在集群的zookeeper端口";
	public static final int HABSE_ZOOPEEPER_PROPERTY_CLIENT_PORT_DEFAULT = 2181;

	static ConfigDef definition = new ConfigDef().define(HBASE_ZOOKEEPER_QUORUM_CONFIG, Type.STRING, Importance.HIGH,
			HBASE_ZOOKEEPER_QUORUM_DOC).define(HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_CONFIG, Type.INT,
			HABSE_ZOOPEEPER_PROPERTY_CLIENT_PORT_DEFAULT, Importance.HIGH, HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT_DOC);

	public ClouderaConfig(Map<String, ?> originals) {
		super(definition, originals);
	}

}
