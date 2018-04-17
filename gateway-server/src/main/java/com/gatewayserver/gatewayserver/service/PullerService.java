package com.gatewayserver.gatewayserver.service;

import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.domain.PkpmPullerConfig;
import com.pkpm.httpclientutil.HuaWeiResponse;

import java.util.List;

/**
 * @author wangxiulong
 * @ClassName: IPullerService
 * @Description: 更新状态Service
 * @date 2018年4月8日
 */
public interface PullerService {

    /**
     * @param jobSize 任务条数
     * @param areaCode 区域名称
     * @return List<String>    返回任务Id集合
     * @throws
     * @Title: getJobTasks
     * @Description: 获取所有任务列表
     */
    List<String> getJobTasks(int jobSize, String areaCode);

    /**
     * @param jobId 任务Id
     * @return PkpmOperatorStatus    返回任务详情信息
     * @throws
     * @Title: getJobDetail
     * @Description: 获取任务详情
     */
    PkpmOperatorStatus getJobDetail(String jobId);

    /**
     * @return List<PkpmPullerConfig>    配置列表
     * @throws
     * @Title: getConfig
     * @Description: 获取Puller所有配置
     */
    List<PkpmPullerConfig> getConfig();


    /**
     * @param jobId  任务Id
     * @param status 状态（成功：SUCCESS，失败：FAILED）
     * @return int    返回操作行数
     * @throws
     * @Title: updateJobTask
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    int updateJobTask(String jobId, String status);


    /**
     * @param jobId        任务Id
     * @param projectId    项目Id
     * @param operatorType 操作类型（DESKTOP:创建桌面,DELETE::删除桌面,CHANGE:修改桌面属性,RESIZE:变更桌面规格,BOOT:启动桌面,REBOOT:重启桌面,CLOSE:关闭桌面）
     * @return HuaWeiResponse    返回状态信息
     * @throws
     * @Title: getHuaWeiInfo
     * @Description: 获取华为数据
     */
    HuaWeiResponse getHuaWeiInfo(String jobId, String projectId, String operatorType);


    /**
     * @param jobId  任务Id
     * @param status 状态（初始化：INITIAL,过程中：CREATE，成功：SUCCESS，失败：FAILED）
     * @return int    返回操作行数
     * @throws
     * @Title: updateJobDetail
     * @Description: 更新任务详情状态，同时插入任务更新日志
     */
    int updateJobDetail(String jobId, String status);
}
