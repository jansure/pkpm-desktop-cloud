package com.cabr.pkpm.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/4/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SubscriptionServiceImplTest {

    @Autowired
    SubscriptionServiceImpl service;

    @Test
    public void getAvailableComputerName() throws Exception {
        String availableName = service.getAvailableComputerName(1, 1);
        System.out.println(availableName);
    }

}