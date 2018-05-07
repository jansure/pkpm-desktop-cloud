package com.cabr.pkpm.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cabr.pkpm.service.IVideoCaptureService;

/**
 * 
 * @ClassName: VideoCaptureServiceImplTest  
 * @Description: 视频截图测试
 * @author wangxiulong  
 * @date 2018年5月7日  
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoCaptureServiceImplTest {

    @Autowired
    IVideoCaptureService service;

    @Test
    public void getAvailableComputerName() throws Exception {
    	boolean isOk = service.initData();
        System.out.println(isOk);
    }

}