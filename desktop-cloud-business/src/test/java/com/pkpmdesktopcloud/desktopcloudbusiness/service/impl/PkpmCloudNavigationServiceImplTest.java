package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudNavigationService;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

import java.util.List;

/**
 * @author yangpengfei
 * @description
 * @date 2018/5/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PkpmCloudNavigationServiceImplTest {

    @Autowired
    PkpmCloudNavigationService service;

    @Test
    public void testGetNavTree() throws Exception {
    	List<PkpmCloudNavigation> navTree = service.getNavTree();
    	log.info(JSON.toJSONString(navTree));
    }

}