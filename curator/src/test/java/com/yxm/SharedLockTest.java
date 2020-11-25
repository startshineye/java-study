package com.yxm;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
public class SharedLockTest {

    public static void main(String[] args) throws Exception{
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();

         InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, "/locks/lock_01");
         lock.acquire();
         Thread.sleep(3000);
         lock.release();
    }
}
