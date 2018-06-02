package com.pkpmcloud.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class WhitelistDaoTest {
    @Autowired
    WhitelistDao dao;

    @Test
    public void listProjectWhitelist() throws Exception {
        Set<String > result =dao.listComputersInWhitelist("3e6d7eda4094efc92d31aa010945980");
        System.out.println(result);
    }

}