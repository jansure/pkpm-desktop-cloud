package com.pkpmcloud.fileserver.client;

import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import com.pkpmcloud.fileserver.conn.DefaultCommandExecutor;
import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorageNodeInfo;
import com.pkpmcloud.fileserver.model.StorageState;
import com.pkpmcloud.fileserver.pool.ConnectionPool;
import com.pkpmcloud.fileserver.pool.PooledConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/21 16:59 <br/>
 */
public class DefaultTrackerClientTest {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultTrackerClientTest.class);

    private static ConnectionPool connectionPool;
    private static TrackerClient trackerClient;

    @Before
    public void init() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(500, 500);
        GenericKeyedObjectPoolConfig conf = new GenericKeyedObjectPoolConfig();
        conf.setMaxTotal(200);
        conf.setMaxTotalPerKey(200);
        conf.setMaxIdlePerKey(100);
        connectionPool = new ConnectionPool(pooledConnectionFactory, conf);
        Set<String> trackerSet = new HashSet<String>();
        trackerSet.add("139.159.254.232:22122");
        trackerSet.add("139.159.254.106:22122");
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor(trackerSet, connectionPool);
        trackerClient = new DefaultTrackerClient(commandExecutor);
    }

    @After
    public void close() {
        connectionPool.close();
    }

    @Test
    public void getStorageNodeTest() {
        StorageNode storageNode = trackerClient.getStorageNode();
        logger.info("####===== " + storageNode);
    }

    @Test
    public void getStorageNodeTest2() {
        StorageNode storageNode = trackerClient.getStorageNode("group1");
        logger.info("####===== " + storageNode);

        storageNode = trackerClient.getStorageNode("group2");
        logger.info("####===== " + storageNode);
    }

    @Test
    public void getFetchStorageTest() {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage("group1", "M00/00/00/wKg4i1gxz_6AIPPsAAiCQSk77jI661.png");
        logger.info("#####===== " + storageNodeInfo);
    }

    @Test
    public void getFetchStorageAndUpdateTest() {
        StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate("group1", "M00/00/00/wKg4i1gxz_6AIPPsAAiCQSk77jI661.png");
        logger.info("#####===== " + storageNodeInfo);
    }

    @Test
    public void getGroupStatesTest() {
        List<GroupState> list = trackerClient.getGroupStates();
        for (GroupState groupState : list) {
            logger.info("#####===== " + groupState);
        }
    }

    @Test
    public void getStorageStatesTest() {
        List<StorageState> list = trackerClient.getStorageStates("group1");
        for (StorageState storageState : list) {
            logger.info("#####===== " + storageState);
        }
    }

    @Test
    public void getStorageStateTest() {
        StorageState storageState = trackerClient.getStorageState("group1", "192.168.10.128");
        logger.info("#####===== " + storageState);

        storageState = trackerClient.getStorageState("group1", "192.168.56.139");
        logger.info("#####===== " + storageState);
    }

    @Test
    public void deleteStorageTest() {
        boolean flag = trackerClient.deleteStorage("group1", "192.168.10.128");
        logger.info("#####===== " + flag);
    }
}
