package com.yxm;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 公平锁测试
 */
public class FailLockTest {

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

        RLock fairLock = redisson.getFairLock("anyLock");
        fairLock.lock();
        fairLock.unlock();

    }
}
