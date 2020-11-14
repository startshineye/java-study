package com.yxm;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
/**
 * 可重入锁
 * @author yexinming
 * @date 2020-11-14
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws Exception {
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();
        /**
         * 2、创建互斥锁
         */
        InterProcessMutex lock = new InterProcessMutex(client, "/locks/lock_01");//在客户端 /locks/lock_01 处创建节点
        lock.acquire();//获取锁
        Thread.sleep(1000);
        lock.release();//释放锁
    }

}
