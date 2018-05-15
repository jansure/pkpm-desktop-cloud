package com.pkpmcloud.model;

import com.pkpmcloud.constants.ApiConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xuhe
 * @description
 * @date 2018/5/15
 */
@Data
@Slf4j
public class BootProperty {

    private int recordQueryInterval;
    private int shutdownOver;
    private int corePoolSize;
    private int maximumPoolSize;
    private int taskPerThread;
    private int httpConnectionTimeout;

    public BootProperty() {
        this.recordQueryInterval = ApiConst.RECORD_QUERY_INTERVAL;
        this.shutdownOver = ApiConst.SHUTDOWN_OVER_MINUTE;
        this.corePoolSize = ApiConst.CORE_POOL_SIZE;
        this.maximumPoolSize = ApiConst.MAXIMUM_POOL_SIZE;
        this.taskPerThread=ApiConst.TASK_PER_THREAD;
        this.httpConnectionTimeout = ApiConst.HTTP_CONNECTION_TIMEOUT;
        log.info("====>>>>>>AUTO:自动设置成功");
        log.info("====>>>>>>{}",this.toString());
    }

    public BootProperty(String recordQueryInterval, String shutdownOver, String corePoolSize, String maximumPoolSize, String taskPerThread,String httpConnectionTimeout) {
        this.recordQueryInterval = ApiConst.RECORD_QUERY_INTERVAL;
        this.shutdownOver = ApiConst.SHUTDOWN_OVER_MINUTE;
        this.corePoolSize = ApiConst.CORE_POOL_SIZE;
        this.maximumPoolSize = ApiConst.MAXIMUM_POOL_SIZE;
        this.taskPerThread=ApiConst.TASK_PER_THREAD;
        this.httpConnectionTimeout = ApiConst.HTTP_CONNECTION_TIMEOUT;

        if (null!=recordQueryInterval) {
            this.recordQueryInterval = Integer.parseInt(recordQueryInterval);
        }
        if (null!=shutdownOver) {
            this.shutdownOver = Integer.parseInt(shutdownOver);
        }
        if (null!=corePoolSize) {
            this.corePoolSize = Integer.parseInt(corePoolSize);
        }
        if (null!=maximumPoolSize) {
            this.maximumPoolSize = Integer.parseInt(maximumPoolSize);
        }
        if (null!=taskPerThread) {
            this.taskPerThread = Integer.parseInt(taskPerThread);
        }
        if (null!=httpConnectionTimeout) {
            this.httpConnectionTimeout = Integer.parseInt(httpConnectionTimeout);
        }
        log.info("====>>>>>>MANUAL:手动设置启动参数成功");
        log.info("====>>>>>>{}",this.toString());
    }

    @Override
    public String toString() {
        return "BootProperty{" +
                "查询时间间隔=" + recordQueryInterval +
                ", 桌面关机断开时间超时=" + shutdownOver +
                ", 核心线程数=" + corePoolSize +
                ", 最大线程数=" + maximumPoolSize +
                ", 任务/线程=" + taskPerThread +
                ", http连接超时时间=" + httpConnectionTimeout +
                '}';
    }
}
