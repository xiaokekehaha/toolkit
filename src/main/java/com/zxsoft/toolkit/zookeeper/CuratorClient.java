package com.zxsoft.toolkit.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by xuwenjuan on 16/10/19.
 */
public class CuratorClient {

    private static CuratorFramework client;

    static {
        client = CuratorFrameworkFactory.builder().connectString("").retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
        client.start();
    }

    public static synchronized CuratorFramework getInstance() {
        return client;
    }

}
