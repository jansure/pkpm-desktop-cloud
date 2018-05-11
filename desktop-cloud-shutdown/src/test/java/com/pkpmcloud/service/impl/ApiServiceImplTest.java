package com.pkpmcloud.service.impl;

import com.pkpmcloud.dao.ProjectDAO;
import com.pkpmcloud.model.Desktop;
import com.pkpmcloud.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ApiServiceImplTest {

    @Autowired
    ApiServiceImpl service;

    @Autowired
    ProjectDAO dao;


    @Test
    public void invokeDesktopShutdownShell() throws Exception {
        List<Project> projects = dao.listValidProject();
        /*service.invokeDesktopShutdownShell(projects.get(0));*/
    }


}
