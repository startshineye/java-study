package com.yxm;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws Exception{
        /**
         * 1、创建 zookeeper集群客户端
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.8,192.168.1.9", retryPolicy);
        client.start();

        /**
         * 2、创建节点
         */
        client.create().creatingParentsIfNeeded()//如果父目录不存在，则先创建父目录
                .forPath("/my/path", "Hello World!".getBytes());

        /**
         * 3、查询数据
         */
        byte[] bytes = client.getData().forPath("/my/path");
        System.out.println(new String(bytes));
    }
}
