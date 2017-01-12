package com.zxsoft.toolkit.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.data.Stat;

/**
 * Created by xuwenjuan on 16/10/19.
 */
public class DataCuratorWatch {

    private CuratorFramework client;

    public DataCuratorWatch(CuratorFramework client) {
        this.client = client;
    }

    public PathChildrenCache monitorPathWatch(String path, PathChildrenCacheListener listener) {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.getListenable().addListener(listener);
        return pathChildrenCache;
    }

    /**
     * 获取zk指定路径的值
     * @param path
     * @param defaultValue
     * @return
     */
    public String getValue(String path, String defaultValue) {
        String value = defaultValue;
        try {
            Stat stat = client.checkExists().forPath(path);
            if (stat == null) {
                client.create().creatingParentsIfNeeded().forPath(path, defaultValue.getBytes());
            } else {
                value = new String(client.getData().forPath(path));
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 更新zk指定位置的值
     * @param path
     * @param value
     * @throws Exception
     */
    public void updateValue(String path, String value) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            client.create().creatingParentsIfNeeded().forPath(path, value.getBytes());
        } else {
            client.setData().forPath(path, value.getBytes());
        }
    }

}
