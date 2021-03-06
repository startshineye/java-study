package com.yxm;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class Application{
    public static void main( String[] args ) throws Exception{
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

        //2.简单用一下分布式锁的功能
        /**
         * 一个线程内加2次锁 并且不释放锁
         */
       /* RLock lock = redisson.getLock("anyLock");
        lock.lock();
        Thread.sleep(1000);
        lock.lock();
        Thread.sleep(1000);
        lock.unlock();
        Thread.sleep(1000);
        lock.unlock();

        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);

        RMap<String, Object> map = redisson.getMap("anyMap");
        map.put("foo", "bar");

        map = redisson.getMap("anyMap");
        System.out.println(map.get("foo"));*/


        RLock fairLock = redisson.getFairLock("anyLock");
        fairLock.lock();
        fairLock.unlock();
    }
}
