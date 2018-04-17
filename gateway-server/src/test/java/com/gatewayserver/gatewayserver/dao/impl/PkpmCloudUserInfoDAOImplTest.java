package com.gatewayserver.gatewayserver.dao.impl;

import com.gatewayserver.gatewayserver.domain.PkpmCloudUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PkpmCloudUserInfoDAOImplTest {

    @Autowired
    PkpmCloudUserInfoDAOImpl userInfoDAO;

    @Test
    public void selectById() throws Exception {
        PkpmCloudUserInfo userInfo = userInfoDAO.selectById(1);
        System.out.println(userInfo.getUserName());
    }

}