package com.pkpmcloud.fileserver.controller;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desktop.utils.page.ResultObject;
import com.pkpmcloud.fileserver.client.TrackerClient;
import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.service.FileServiceTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FastDFSControllerTest {
	
	@Resource
	private TrackerClient trackerClient;
	@Resource
	private FastDFSController fastDFSController;
	
	@Test
    public void getGroupStatesTest() {
		List<GroupState> list = trackerClient.getGroupStates();
		
		for (GroupState groupState : list) {
			System.out.println(groupState.toString());
		}
    }

}
