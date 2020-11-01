package com.yxm;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Hello world!
 *
 */
public class Application{
    public static void main( String[] args ){
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
        RLock lock = redisson.getLock("anyLock");
        lock.lock();
        lock.unlock();

        RMap<String, Object> map = redisson.getMap("anyMap");
        map.put("foo", "bar");

        map = redisson.getMap("anyMap");
        System.out.println(map.get("foo"));
    }
}
