package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PkpmCloudSubscriptionDAOImplTest {

    @Autowired
    PkpmCloudSubscriptionDAOImpl dao;

    @Test
    public void countByAdIdExceptFailedSubs() throws Exception {
        dao.countByAdIdExceptFailedSubs(1);
    }

}