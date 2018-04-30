package com.gatewayserver.gatewayserver.service.impl;

import com.gatewayserver.gatewayserver.service.GatewayServerParam;
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
public class GatewayServerParamImplTest {

    @Autowired
    GatewayServerParam pkpmCloudService;

    @Test
    public void testGetAdAndProject() throws Exception {

        String areaCode = "cn-north-1";
        String ouName = "远大北京公司/销售部";
        pkpmCloudService.getAdAndProject(areaCode, ouName);

    }
    
    @Test
    public void testGetProjectDef() throws Exception {

        String projectId = "58cd927978ff4f85b16ee64468da0e53";
        String areaCode = "cn-south-1";
        pkpmCloudService.getProjectDef(projectId, areaCode);

    }

}