package com.yxm;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class SemaphoreTest {
    public static void main(String[] args) throws Exception{
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();
        /**
         * 2、获取信号量
         */
        InterProcessSemaphoreV2 semaphoreV2 = new InterProcessSemaphoreV2(client, "/semaphore/semaphore_01", 3);
        Lease lease = semaphoreV2.acquire();
        Thread.sleep(3000);
        semaphoreV2.returnLease(lease);
    }
}
