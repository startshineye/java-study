package com.yxm;

import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Date;

public class CountDownLatchTest {
    public static void main(String[] args) throws Exception{
        //1.配置集群节点
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://192.168.1.8:7001")
                .addNodeAddress("redis://192.168.1.8:7002")
                .addNodeAddress("redis://192.168.1.8:7003")
                .addNodeAddress("redis://192.168.1.9:7001")
                .addNodeAddress("redis://192.168.1.9:7002")
                .addNodeAddress("redis://192.168.1.9:7003");
        final RedissonClient redisson = Redisson.create(config);

        RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
        /**
         * 1、主线程设置countDownlatch的个数
         */
        latch.trySetCount(3);
        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]设置了必须有3个线程执行countDown，进入等待中。。。");

        /***
         * 2、开启3个线程去获取锁
         */
        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]在做一些操作，请耐心等待。。。。。。");
                        Thread.sleep(3000);
                        RCountDownLatch localLatch = redisson.getCountDownLatch("anyCountDownLatch");
                        /**
                         * 获取的时候执行countDown
                         */
                        localLatch.countDown();
                        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]执行countDown操作");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
        /**
         * 3、主线程阻塞等待当有3个满足时候，继续往下面走
         */
        latch.await();
        System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]收到通知，有3个线程都执行了countDown操作，可以继续往下走");
    }
}
