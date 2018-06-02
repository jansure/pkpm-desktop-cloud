package com.idserver.dao;

import com.idserver.model.Identify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentifyDAOTest {

    @Autowired
    IdentifyDAO dao;

    @Test
    public void insert() throws Exception {
        Identify identify = new Identify();
        identify.setAdId(1);
        identify.setIdentifyPrefix("lvjian");
        identify.setIdentifyIndex(2);
        identify.setId(1);
        dao.insert(identify);
    }

    @Test
    public void count(){
        Identify identify =new Identify();
        identify.setAdId(1);
        identify.setIdentifyPrefix("lvjian");
        int result = dao.count(identify);
        System.out.println(result);
    }

}