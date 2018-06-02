package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import lombok.extern.slf4j.Slf4j;
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
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PkpmCloudSubscriptionDAOTest {
    @Autowired
    PkpmCloudSubscriptionDAO dao;

    @Test
    public void countByAdId() throws Exception {
        log.info("进入Test");
        int res=dao.countByAdIdExceptFailedSubs(1);
        System.out.println(res);
    }

}