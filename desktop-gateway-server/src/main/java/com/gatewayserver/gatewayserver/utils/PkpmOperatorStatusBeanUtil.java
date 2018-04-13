package com.gatewayserver.gatewayserver.utils;

import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.google.common.base.Preconditions;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/4/11
 */
public class PkpmOperatorStatusBeanUtil {

    /**
     * @param operatorStatus 操作实体
     * @return void
     * @Description 插入数据库前，校验所有参数为非空
     * @Author xuhe
     */
    public static void checkNotNull(PkpmOperatorStatus operatorStatus) {

        Preconditions.checkNotNull(operatorStatus.getJobId(), "jobId不能为空");
        Preconditions.checkNotNull(operatorStatus.getProjectId(), "projectId不能为空");
        Preconditions.checkNotNull(operatorStatus.getUserId(), "userId不能为空");
        Preconditions.checkNotNull(operatorStatus.getSubsId(), "subsId不能为空");
        Preconditions.checkNotNull(operatorStatus.getAdId(), "adId不能为空");
        Preconditions.checkNotNull(operatorStatus.getUserName(), "userName不能为空");
        Preconditions.checkNotNull(operatorStatus.getDesktopId(), "desktopId不能为空");
        Preconditions.checkNotNull(operatorStatus.getComputerName(), "computerName不能为空");
        Preconditions.checkNotNull(operatorStatus.getOperatorType(), "operatorType不能为空");
        Preconditions.checkNotNull(operatorStatus.getStatus(), "status不能为空");


    }
}
