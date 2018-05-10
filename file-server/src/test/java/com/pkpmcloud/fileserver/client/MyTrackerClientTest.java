package com.pkpmcloud.fileserver.client;

import java.util.List;

import javax.annotation.Resource;

import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.model.StorageNode;
import com.pkpmcloud.fileserver.model.StorageNodeInfo;
import com.pkpmcloud.fileserver.model.StorageState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTrackerClientTest {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyTrackerClientTest.class);

	
	@Resource
	private TrackerClient trackerClient;

	@Test
	public void getStorageNodeTest() {
		StorageNode storageNode = trackerClient.getStorageNode();
		logger.info("#===== " + storageNode);
	}

	@Test
	public void getStorageNodeTest2() {
		StorageNode storageNode = trackerClient.getStorageNode("group1");
		logger.info("##===== " + storageNode);

		storageNode = trackerClient.getStorageNode("group2");
		logger.info("##===== " + storageNode);
	}

	@Test
	public void getFetchStorageTest() {
		StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorage("group1",
				"M00/00/00/wKg4i1gxz_6AIPPsAAiCQSk77jI6611.png");
		logger.info("###===== " + storageNodeInfo);
	}

	@Test
	public void getFetchStorageAndUpdateTest() {
		StorageNodeInfo storageNodeInfo = trackerClient.getFetchStorageAndUpdate("group1",
				"M00/00/00/wKg4i1gxz_6AIPPsAAiCQSk77jI661.png");
		logger.info("#####===== " + storageNodeInfo);
	}

	@Test
	public void getGroupStatesTest() {
		List<GroupState> list = trackerClient.getGroupStates();
		for (GroupState groupState : list) {
			logger.info("######===== " + groupState);
		}
	}

	@Test
	public void getStorageStatesTest() {
		List<StorageState> list = trackerClient.getStorageStates("group1");
		for (StorageState storageState : list) {
			logger.info("######===== " + storageState);
		}
	}

	@Test
	public void getStorageStateTest() {
		StorageState storageState = trackerClient.getStorageState("group1", "139.159.254.86");
		logger.info("########===== " + storageState);

		storageState = trackerClient.getStorageState("group2", "139.159.254.148");
		logger.info("########===== " + storageState);
	}

//	@Test
//	public void deleteStorageTest() {
//		boolean flag = trackerClient.deleteStorage("group1", "192.168.10.128");
//		logger.info("#####===== " + flag);
//	}
}
