package com.zxsoft.toolkit.zookeeper.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * Created by xuwenjuan on 17/2/21.
 */
public class PathChildrenCacheListenerImpl implements PathChildrenCacheListener {

    @Override
    public void childEvent(CuratorFramework curatorFramework,
                           PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        switch (pathChildrenCacheEvent.getType()) {
            case CHILD_ADDED:
                System.out.print("CHILD_ADDED");
                break;
            case CHILD_REMOVED:
                System.out.print("CHILD_REMOVED");
                break;
            case CHILD_UPDATED:
                System.out.print("CHILD_UPDATED");
                break;
            default:
                System.out.print("NOTHING");
        }
    }
}
