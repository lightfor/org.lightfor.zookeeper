package org.lightfor.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * curator test
 * Created by Light on 2017/7/29.
 */
public class CuratorTest {

    public void test() throws Exception {

        String zookeeperConnectionString = "192.168.1.230:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                //do something;
            }
        });
        client.start();

        //client.create().forPath("/my/path", myData)

        InterProcessMutex lock = new InterProcessMutex(client, "lockPath");
        if ( lock.acquire(2, TimeUnit.SECONDS) )
        {
            try
            {
                // do some work inside of the critical section here
            }
            finally
            {
                lock.release();
            }
        }


    }
}
