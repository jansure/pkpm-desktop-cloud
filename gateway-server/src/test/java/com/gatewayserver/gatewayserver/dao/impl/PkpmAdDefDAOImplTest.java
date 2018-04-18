package com.gatewayserver.gatewayserver.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gateway.common.domain.PkpmAdDef;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PkpmAdDefDAOImplTest {

    @Autowired
    PkpmAdDefDAOImpl adDefDAO;

    @Test
    public void selectById() throws Exception {
        PkpmAdDef adDef = adDefDAO.selectById(1);
        Assert.assertNotNull(adDef);
        System.out.println(adDef.getAdAdminAccount());
    }

}