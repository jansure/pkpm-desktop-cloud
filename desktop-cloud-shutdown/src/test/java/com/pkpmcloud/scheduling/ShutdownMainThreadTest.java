package com.pkpmcloud.scheduling;

import com.google.common.base.Preconditions;
import com.pkpmcloud.dao.ProjectDao;
import com.pkpmcloud.dao.mapper.ProjectMapper;
import com.pkpmcloud.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ShutdownMainThreadTest {
    @Autowired
    ProjectDao dao;
    @Autowired
    ShutdownMainThread mainThread;
    @Test
    public void invokeDesktopShutdownShell() throws Exception {
        List<Project> projectList =dao.listValidProject();
        log.info("{}---",projectList.get(0));
        Preconditions.checkNotNull(projectList.get(0).getProjectId());
        mainThread.invokeDesktopShutdownShell(projectList.get(0));
    }

}