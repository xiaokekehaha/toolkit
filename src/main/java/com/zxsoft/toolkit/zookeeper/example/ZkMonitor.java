package com.zxsoft.toolkit.zookeeper.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by xuwenjuan on 17/2/21.
 */
public class ZkMonitor {

    private CuratorFramework client;
    private ExecutorService  executorService;

    public ZkMonitor(CuratorFramework client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    public void monitor(String path) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true, false, executorService);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListenerImpl());
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        client.start();
        ExecutorService executorService = new ScheduledThreadPoolExecutor(3);
        ZkMonitor zkMonitor = new ZkMonitor(client, executorService);
        zkMonitor.monitor("/zookeeper");
    }
}
