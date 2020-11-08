package com.yxm;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 读写锁
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        System.out.println("1");
        //1.配置集群节点
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://192.168.1.8:7001")
                .addNodeAddress("redis://192.168.1.8:7002")
                .addNodeAddress("redis://192.168.1.8:7003")
                .addNodeAddress("redis://192.168.1.9:7001")
                .addNodeAddress("redis://192.168.1.9:7002")
                .addNodeAddress("redis://192.168.1.9:7003");
        RedissonClient redisson = Redisson.create(config);

        RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
        // 最常见的使用方法
        rwlock.readLock().lock();
        rwlock.readLock().unlock();

        rwlock.writeLock().lock();
        rwlock.writeLock().unlock();
    }
}
