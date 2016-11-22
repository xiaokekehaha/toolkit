package com.zxsoft.toolkit.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

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
}
