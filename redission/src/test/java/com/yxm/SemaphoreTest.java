package com.yxm;

import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Date;

public class SemaphoreTest {
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

        //1、获取semaphore对象
         final RSemaphore semaphore = redisson.getSemaphore("semaphore");
        //2、设置同时允许多少个客户端获取锁
        semaphore.trySetPermits(3);

        //3、开多个客户端-去尝试获取锁
        for(int i=0;i<10;i++){
             new Thread(new Runnable() {
                 public void run() {
                     try {
                         System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]尝试获取Semaphore锁");
                         semaphore.acquire();
                         System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]成功获取到了Semaphore锁，开始工作");
                         Thread.sleep(3000);
                         semaphore.release();
                         System.out.println(new Date() + "：线程[" + Thread.currentThread().getName() + "]释放Semaphore锁");
                         }catch (Exception e){
                         e.printStackTrace();
                     }
                 }
             }).start();
        }
    }
}
