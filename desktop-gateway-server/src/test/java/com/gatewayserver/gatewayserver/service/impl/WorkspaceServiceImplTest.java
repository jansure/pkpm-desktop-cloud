package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.dao.PkpmOperatorStatusDAO;
import com.gatewayserver.gatewayserver.dao.impl.PkpmCloudUserInfoDAOImpl;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.Desktop;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.gatewayserver.gatewayserver.thread.JobThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangpengfei
 * @Description 测试接口类
 * @time 2018年3月29日 下午4:13:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkspaceServiceImplTest {

    @Autowired
    DesktopServiceImpl workspaceServiceImpl;

    @Autowired
    DesktopService workspaceService;

    @Autowired
    PkpmCloudUserInfoDAOImpl userInfoDAO;

    @Autowired
    PkpmOperatorStatusDAO pkpmOperatorStatusDAO;
    @Autowired
    DesktopService workspaceService1;

    @Test
    public void changeDesktopSpec() {

        CommonRequestBean requestBean = new CommonRequestBean();
        requestBean.setProjectId("9487c2cb4c4d4c828868098b7a78b497");
        requestBean.setAdId(1);
        requestBean.setUserName("xuhe10");

        Desktop desktop = new Desktop();
        desktop.setDesktopId("94d468c0-c88b-4735-8091-10b9f791e04c");
        desktop.setProductId("workspace.c2.large.windows");
        List<Desktop> desktops = new ArrayList<>();
        desktops.add(desktop);
        requestBean.setDesktops(desktops);

        ResultObject resultObject = workspaceServiceImpl.changeDesktopSpec(requestBean);
        System.out.println(resultObject.getCode());
        System.out.println(resultObject.getMessage());
        System.out.println(resultObject.getData());
    }

    @Test
    public void testUpdateOperStatus() throws Exception {
        String jobId = "ff80808262863b070162a7e848600177";
        String userName = "xuhe10";
        int adId = 1;
        long seconds = 60;
        workspaceService.updateOperatorStatus(jobId, userName, adId, seconds);
        System.out.println("===updateOperStatus===");

    }

    @Test
    public void testJobThread() {
        JobThread jobThread = new JobThread(pkpmOperatorStatusDAO, workspaceService1);
        Thread t1 = new Thread(jobThread, "线程一");
//		Thread t2 = new Thread(new JobThread(),"线程二");
//		Thread t3 = new Thread(jobThread,"线程三");
        t1.start();
//		t2.start();
//		t3.start();
    }

}