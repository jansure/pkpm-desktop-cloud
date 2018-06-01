package com.gatewayserver.gatewayserver.dao;

import java.util.List;

import com.gateway.common.domain.PkpmOperatorStatus;

/**
 * @author yangpengfei
 * @Description 操作状态处理接口
 * @time 2018年3月29日 上午10:44:31
 */
public interface PkpmOperatorStatusDAO {
    /**
     * 通过jobId获取PkpmOperatorStatus信息(仅查询未完成的创建桌面类的任务)
     *
     * @param jobId 任务id
     * @return 任务
     */
    PkpmOperatorStatus selectByJobId(String jobId);

    /**
     * @Description 查询未完成的开户任务
     * @author yangpengfei
     * @time 2018年3月29日 上午10:46:43
     */
    List<PkpmOperatorStatus> selectNotFinished();

    // 更新记录
    Integer update(PkpmOperatorStatus pkpmOperatorStatus);

    /**
     * 保存桌面状态记录
     *
     * @param pkpmOperatorStatus
     */
    Integer save(PkpmOperatorStatus pkpmOperatorStatus);

    /**
     * @param jobId 任务Id
     * @return PkpmOperatorStatus    返回任务详情信息
     * @throws
     * @Title: selectNotFinishedByJobId
     * @Description: 获取未完成的任务详情（Puller使用）
     */
    PkpmOperatorStatus selectNotFinishedByJobId(String jobId);


    PkpmOperatorStatus selectOperatorStatus(PkpmOperatorStatus pkpmOperatorStatus);

}
