package com.pkpmcloud.scheduling;

import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.dao.ProjectDao;
import com.pkpmcloud.model.Project;
import com.pkpmcloud.thread.ShutdownMainThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    ShutdownMainThread mainThread;

    private static String projectId;
    private static int shutDownOver;
    private static int recordQueryInterval;
    static {
        String proj = System.getProperty("projectId");

    }


    public static final long TIME_INTERVAL = 60* 1000;

    @Scheduled(fixedRate = TIME_INTERVAL)
    private void shutdown() throws ExecutionException, InterruptedException {

        String now = LocalDateTime.now().format(ApiConst.DATE_TIME_FORMATTER);
        log.info("");
        log.info("====>>>>>>定时任务 启动时间{} 启动间隔{}ns", now,TIME_INTERVAL);
        List<Project> projects = projectDao.listValidProject();
        for (int i = 0; i < projects.size(); i++) {
            mainThread.invokeDesktopShutdownShell(projects.get(i));
        }
    }


}
