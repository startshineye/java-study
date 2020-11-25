package com.yxm;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class MultiLockTest {
    public static void main(String[] args) throws Exception{
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();

        /**
         * 2、创建3个锁
         */
        InterProcessLock lock1 = new InterProcessMutex(client, "/locks/lock_01");
        InterProcessLock lock2 = new InterProcessMutex(client, "/locks/lock_02");
        InterProcessLock lock3 = new InterProcessMutex(client, "/locks/lock_03");

         List<InterProcessLock> locks = new ArrayList<InterProcessLock>();
         locks.add(lock1);
         locks.add(lock2);
         locks.add(lock3);

        /**
         * 3、创建多锁
         */
        InterProcessMultiLock multiLock = new InterProcessMultiLock(locks);
        multiLock.acquire();
        multiLock.release();
    }
}
