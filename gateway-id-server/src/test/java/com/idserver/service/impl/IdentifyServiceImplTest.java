package com.idserver.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentifyServiceImplTest {

    @Autowired
    IdentifyServiceImpl identifyGenerator;

    @Test
    public void getAvailableIdentity() throws Exception {
        String result = identifyGenerator.getAvailableIdentity("pkpm", 2);
        System.out.println(result);
    }

}