package com.pkpmcloud.scheduling;

import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.dao.ProjectDAO;
import com.pkpmcloud.model.Project;
import com.pkpmcloud.service.impl.ApiServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xuhe
 * @description 自动定时任务
 * @date 2018/5/9
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    ApiServiceImpl apiService;

    private static String[] projects;

    private static int corePoolSize = 4;//线程池维护线程的最少数量
    private static int maxPoolSize = 10;//线程池维护线程的最大数量
    private static long keepAlive = 60L;//允许的空闲时间

    private static int queueCapacity = 8; //缓存队列


    static {
        String projectList = System.getProperty("projectId");
        if (!StringUtils.isEmpty(projectList)) {
            projects = projectList.split(",");
        }

    }


    public static final long TIME_INTERVAL = 5 * 60 * 1000;

    @Scheduled(fixedRate = TIME_INTERVAL)
    private void shutdown() {

        String now = LocalDateTime.now().format(ApiConst.DATE_TIME_FORMATTER);
        log.info(">>>>>>定时任务 启动时间{}", now);
        log.info("进入定时任务");
        List<Project> projects = projectDAO.listValidProject();
        apiService.invokeDesktopShutdownShell(projects.get(0));


    }


}
