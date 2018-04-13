package com.gatewayserver.gatewayserver.service.impl;

import com.gatewayserver.gatewayserver.service.CloudOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @Description TODO
 * @author yangpengfei
 * @time 2018年4月13日 上午9:01:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudOrderServiceImplTest {

    @Autowired
    CloudOrderService pkpmCloudService;

    @Test
    public void testGetAdAndProject() throws Exception {

        String areaCode = "cn-north-1";
        String ouName = "";
        pkpmCloudService.getAdAndProject(areaCode, ouName);

    }

}