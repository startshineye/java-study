package com.yxm;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
public class SharedReadWriteLockTest {
    public static void main(String[] args) throws Exception{
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();
        /**
         * 2、创建读写锁
         */
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/locks/lock_01");
       /* readWriteLock.readLock().acquire();//获取读锁
        Thread.sleep(3000);
        readWriteLock.readLock().release();//释放读锁
        readWriteLock.writeLock().acquire();//获取写锁
        readWriteLock.writeLock().release();//释放写锁*/
        /**
         * 先加写锁+读锁
         */
        readWriteLock.writeLock().acquire();
        readWriteLock.readLock().acquire();
    }
}
