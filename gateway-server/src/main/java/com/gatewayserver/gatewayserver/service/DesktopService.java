package com.gatewayserver.gatewayserver.service;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.DesktopCreation;
import com.gateway.common.dto.DesktopParam;
import com.gateway.common.dto.DesktopRequest;
import com.gateway.common.dto.JobBean;
import com.gateway.common.dto.desktop.DesktopSpecResponse;

import java.util.List;

/**
 * @author yangpengfei
 * @date 2018/03/14
 */
public interface DesktopService {
	/**
	 * 生成X-Auth-Token
	 */
	String createToken(String projectId);

	/**
	 * @Description: 创建Ad及桌面
	 * @Author: xuhe
	 * @Date:
	 */
	ResultObject createAdAndDesktop(CommonRequestBean commonRequestBean);

	/**
	 * 创建桌面
	 */
	DesktopCreation createDesktop(CommonRequestBean commonRequestBean);

	/**
	 * 查询异步任务执行情况
	 */
	JobBean queryJob(String jobId);

	/**
	 * 按照用户查询用户登陆信息
	 */
	JobBean getLoginInfoById(Integer userId);

	/**
	 * 删除桌面
	 */
	String deleteDesktop(CommonRequestBean commonRequestBean);

	/**
	 * 修改桌面属性
	 */
	String changeDesktop(CommonRequestBean requestBean);
	/**
	 * 变更桌面配置
	 * @author xuhe
	 */
	DesktopSpecResponse changeDesktopSpec(CommonRequestBean requestBean);

    /**
     * 查询桌面详情
     *
     * @param commonRequestBean
     * @return ResultObject
     */
    DesktopRequest queryDesktopDetail(CommonRequestBean commonRequestBean);

    /**
     * 重启、启动、关闭桌面
     *
     * @param commonRequestBean
     * @return
     */
    //ResultObject operateDesktop(DesktopInput info);
    String operateDesktop(CommonRequestBean commonRequestBean);

    /**
     * 查询桌面列表 或者  桌面详情列表
     *
     * @param commonRequestBean
     * @return
     */
    //ResultObject queryDesktopListOrDetail(CommonRequestBean commonRequestBean);
    DesktopRequest queryDesktopListOrDetail(CommonRequestBean commonRequestBean);

	/**
	 * @param jobId
	 *            任务id
	 * @param userName
	 *            用户名
	 * @param adId
	 *            ad域id
	 * @param seconds
	 *            预设的扫描时间间隔（秒）
	 * @Description 查询创建桌面任务执行情况，并更新操作状态表
	 * @author yangpengfei
	 * @time 2018年3月29日 下午5:00:00
	 */
	void updateOperatorStatus(String jobId, String userName, Integer adId, Long seconds);
}
