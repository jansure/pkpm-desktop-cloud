package com.cabr.pkpm.dao.impl;

import com.cabr.pkpm.dao.SubscriptionDAO;
import com.cabr.pkpm.entity.subscription.SubsCription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/4/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionDAOImplTest {
    @Autowired
    SubscriptionDAO dao;
    @Test
    public void selectBySubId() throws Exception {
        SubsCription subsCription =dao.selectBySubId(152361277080509L);
        System.out.println(subsCription.getProjectId());

    }

}