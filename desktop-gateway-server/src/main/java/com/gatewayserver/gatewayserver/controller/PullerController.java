package com.gatewayserver.gatewayserver.controller;

import com.desktop.constant.JobStatusEnum;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;
import com.gatewayserver.gatewayserver.domain.PkpmPullerConfig;
import com.gatewayserver.gatewayserver.service.PullerService;
import com.pkpm.httpclientutil.HuaWeiResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangxiulong
 * @ClassName: PullerController
 * @Description: 更新状态Controler
 * @date 2018年4月8日
 */
@RestController
@RequestMapping("/puller")
public class PullerController {

    @Autowired
    private PullerService pullerService;

    /**
     * @return ResultObject    返回结果状态
     * @throws
     * @Title: getJobTasks
     * @Description: 获取所有未更新的任务
     */
    @RequestMapping(value = "/getJobTasks", method = RequestMethod.GET)
    public ResultObject getJobTasks(int jobSize) {
        List<String> jobIds = pullerService.getJobTasks(jobSize);

        if (CollectionUtils.isNotEmpty(jobIds)) {
            return ResultObject.success(jobIds, "操作成功!");
        }

        return ResultObject.failure("没有需要更新的任务！");
    }

    /**
     * @param @param  jobId
     * @param @return 参数
     * @return ResultObject    返回结果
     * @throws
     * @Title: getJobDetail
     * @Description: 获取任务详情
     */
    @RequestMapping(value = "/getJobDetail", method = RequestMethod.GET)
    public ResultObject getJobDetail(String jobId) {
        if (StringUtils.isEmpty(jobId)) {
            return ResultObject.failure("任务Id不能为空！");
        }

        PkpmOperatorStatus jobDetails = pullerService.getJobDetail(jobId);
        if (jobDetails != null) {
            //返回对象序列化成字符串，解决自定义对象不同系统之间传输问题
            return ResultObject.success(JsonUtil.serialize(jobDetails), "操作成功!");
        }

        return ResultObject.failure("无此任务！");
    }

    /**
     * @param @return 参数
     * @return ResultObject    返回结果
     * @throws
     * @Title: getConfig
     * @Description: 获取所有的更新时间间隔配置
     */
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public ResultObject getConfig() {

        List<PkpmPullerConfig> configList = pullerService.getConfig();
        if (CollectionUtils.isNotEmpty(configList)) {
            try {
                //将配置集合转换为Map，形式为：（operatorType，seconds）
                Map<String, Integer> configMap = configList.stream().collect(Collectors.toMap(PkpmPullerConfig::getOperatorType, PkpmPullerConfig::getSeconds));
                return ResultObject.success(configMap, "操作成功!");
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResultObject.failure("配置信息读取出错！");
            }

        }

        return ResultObject.failure("没有配置信息！");
    }


    /**
     * @param jobId        任务Id
     * @param projectId    项目Id
     * @param operatorType 操作类型（DESKTOP:创建桌面,DELETE::删除桌面,CHANGE:修改桌面属性,RESIZE:变更桌面规格,BOOT:启动桌面,REBOOT:重启桌面,CLOSE:关闭桌面）
     * @return ResultObject    返回结果
     * @throws
     * @Title: getHuaWeiInfo
     * @Description: 获取华为数据
     */
    @RequestMapping(value = "/getHuaWeiInfo", method = RequestMethod.GET)
    public ResultObject getHuaWeiInfo(String jobId, String projectId, String operatorType) {
        if (StringUtils.isEmpty(jobId)) {
            return ResultObject.failure("jobId不能为空！");
        }

        if (StringUtils.isEmpty(projectId)) {
            return ResultObject.failure("projectId不能为空！");
        }

        if (StringUtils.isEmpty(operatorType)) {
            return ResultObject.failure("operatorType不能为空！");
        }

        HuaWeiResponse huaweiResponse = pullerService.getHuaWeiInfo(jobId, projectId, operatorType);

        if (huaweiResponse != null) {
            //返回对象序列化成字符串，解决自定义对象不同系统之间传输问题
            return ResultObject.success(JsonUtil.serialize(huaweiResponse), "操作成功!");
        }

        return ResultObject.failure("获取失败！");
    }

    /**
     * @param @param  jobId
     * @param @param  status
     * @param @return 参数
     * @return ResultObject    返回结果
     * @throws
     * @Title: updateJobTask
     * @Description: 更新Job任务表
     */
    @RequestMapping(value = "/updateJobTask", method = RequestMethod.POST, consumes = "application/json")
    public ResultObject updateJobTask(String jobId, String status) {
        if (StringUtils.isEmpty(jobId)) {
            return ResultObject.failure("jobId不能为空！");
        }

        if (StringUtils.isEmpty(status)) {
            return ResultObject.failure("status不能为空！");
        }

        //验证状态值的正确性
        try {
            JobStatusEnum.eval(status);
        } catch (Exception ex) {
            return ResultObject.failure(ex.getMessage());
        }

        int num = pullerService.updateJobTask(jobId, status);

        if (num >= 1) {
            return ResultObject.success("操作成功!");
        }

        return ResultObject.failure("更新失败！");
    }

    /**
     * @param @param  jobId
     * @param @param  status
     * @param @return 参数
     * @return ResultObject    返回结果
     * @throws
     * @Title: updateJobDetail
     * @Description: 更新Job任务表
     */
    @RequestMapping(value = "/updateJobDetail", method = RequestMethod.POST)
    public ResultObject updateJobDetail(String jobId, String status) {
        if (StringUtils.isEmpty(jobId)) {
            return ResultObject.failure("jobId不能为空！");
        }

        if (StringUtils.isEmpty(status)) {
            return ResultObject.failure("status不能为空！");
        }

        //验证状态值的正确性
        try {
            JobStatusEnum.eval(status);
        } catch (Exception ex) {
            return ResultObject.failure(ex.getMessage());
        }
        int num = pullerService.updateJobDetail(jobId, status);

        if (num >= 1) {
            return ResultObject.success("操作成功!");
        }

        return ResultObject.failure("更新失败！");
    }

}
