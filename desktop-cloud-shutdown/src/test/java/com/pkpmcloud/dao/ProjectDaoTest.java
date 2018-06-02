package com.pkpmcloud.dao;

import com.pkpmcloud.model.Project;
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
public class ProjectDaoTest {

    @Autowired
    ProjectDao projectDao;

    @Test
    public void listValidProject() throws Exception {
        List<Project> projects = projectDao.listValidProject();
        System.out.println(projects);
    }

}