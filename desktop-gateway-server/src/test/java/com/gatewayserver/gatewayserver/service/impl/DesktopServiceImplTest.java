package com.gatewayserver.gatewayserver.service.impl;

import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.service.DesktopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/4/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DesktopServiceImplTest {

    @Autowired
    DesktopService desktopService;

    @Test
    public void createAdAndDesktop() throws Exception {

        CommonRequestBean info = new CommonRequestBean();
        info.setUserName("glglgl");
        info.setUserLoginPassword("Abc=5678");
        info.setUserEmail("cherishpf@163.com");
        info.setProjectId("9487c2cb4c4d4c828868098b7a78b497");
        info.setImageId("997488ed-fa23-4671-b88c-d364c0405334");// 默认的镜像Workspace-User-Template
        info.setDataVolumeSize(100);
        info.setHwProductId("workspace.c2.large.windows");
        info.setGloryProductName("XUHE-TEST");
        info.setOuName("pkpm");
        info.setAdId(1);
        info.setUserId(145);
        info.setSubsId(4);
        info.setOperatorStatusId(14);// AD
        desktopService.createAdAndDesktop(info);

    }

}