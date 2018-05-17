package com.pkpmcloud.scheduling;

import com.google.common.base.Preconditions;
import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.dao.ProjectDao;
import com.pkpmcloud.model.BootProperty;
import com.pkpmcloud.model.Project;
import com.pkpmcloud.thread.ShutdownMainThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author xuhe
 * @implNote 定时任务类
 */

@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    ShutdownMainThread mainThread;

    private static String projectId;
    private static BootProperty bootProperty;
    private static Boolean isAuto = Boolean.TRUE;

    static {
        projectId = System.getProperty("projectId");
        if (projectId != null) {
            log.info("====>>>>>>手动配置");
            isAuto = Boolean.FALSE;
            bootProperty = new BootProperty(System.getProperty("recordQueryInterval")
                    , System.getProperty("shutdownOver")
                    , System.getProperty("corePoolSize")
                    , System.getProperty("maximumPoolSize")
                    , System.getProperty("taskPerThread")
                    , System.getProperty("httpConnectionTimeout"));
        } else {
            log.info("====>>>>>>自动配置");
            bootProperty = new BootProperty();
        }
    }


    public static final long TIME_INTERVAL = 5 * 60 * 1000;

    @Scheduled(fixedRate = TIME_INTERVAL)
    private void shutdown() throws ExecutionException, InterruptedException {
        String now = LocalDateTime.now().format(ApiConst.DATE_TIME_FORMATTER);
        log.info("====>>>>>>定时任务 启动时间{} 启动间隔{}ns 自动配置:{}", now, TIME_INTERVAL, isAuto);
        /**
         * 根据isAuto判断分别启动自动模式和手动模式
         */
        if (isAuto) {
            shutdownWithAutoParameters();
        } else {
            shutdownWithBootParameters();
        }

    }
    /**
     * 添加启动参数的脚本
     */
    private void shutdownWithBootParameters() {
        Project project = projectDao.getProject(projectId);
        Preconditions.checkNotNull(project, "projectId不存在，请添加数据库记录");

        mainThread.invokeDesktopShutdownShell(project, bootProperty);
    }
    /**
     * 无启动参数关机脚本，采用ApiConstant中默认的常量设置
     */
    private void shutdownWithAutoParameters() {
        List<Project> projects = projectDao.listValidProject();
        for (int i = 0; i < projects.size(); i++) {
            mainThread.invokeDesktopShutdownShell(projects.get(i), bootProperty);
        }
    }

}
