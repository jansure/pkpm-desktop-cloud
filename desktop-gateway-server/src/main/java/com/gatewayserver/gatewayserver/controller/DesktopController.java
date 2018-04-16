package com.gatewayserver.gatewayserver.controller;

import javax.annotation.Resource;

import com.gatewayserver.gatewayserver.service.AdService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.DesktopCreation;
import com.gatewayserver.gatewayserver.dto.DesktopRequest;
import com.gatewayserver.gatewayserver.dto.JobBean;
import com.gatewayserver.gatewayserver.service.DesktopService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangpengfei
 * @date 2018/03/14
 */
@RestController
@Slf4j
@RequestMapping("desktop")
public class DesktopController {
	@Resource
	CommonRequestBeanBuilder commonRequestBeanBuilder;
	@Autowired
	private DesktopService desktopService;
	@Autowired
	private AdService adService;

	/**
	 * 生成token接口
	 *
	 * @param projectId
	 *            项目id
	 * @return
	 */
	@RequestMapping(value = "/createToken/{projectId}", method = RequestMethod.GET)
	public ResultObject createToken(@PathVariable(value = "projectId") String projectId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(projectId), "请求对象的projectId不能为空");
		String token = desktopService.createToken(projectId);
		log.info(token);
		return ResultObject.success(token, "获取token成功");
	}

	/**
	 * 创建桌面接口 先创建AD再传入创建桌面
	 *
	 * @param commonRequestBean
	 *            用户传入信息
	 * @return
	 */
	@PostMapping(value = "/createAdAndDesktop")
	public ResultObject createAdAndDesktop(@RequestBody CommonRequestBean commonRequestBean) {
		adService.addAdUser(commonRequestBean);
		DesktopCreation desktopCreation = desktopService.createDesktop(commonRequestBean);
		Preconditions.checkNotNull(desktopCreation);
		Preconditions.checkNotNull(desktopCreation.getJobId());
		Preconditions.checkArgument(desktopCreation.getJobId().length() > 0);
		log.info("createDesktop job_id = " + desktopCreation.getJobId());
		return ResultObject.success(desktopCreation.getJobId(), "正在创建桌面......");
	}

	/**
	 * 创建桌面接口
	 *
	 * @param commonRequestBean
	 *            用户传入信息
	 * @return
	 */
	@RequestMapping(value = "/createDesktop", method = RequestMethod.POST, consumes = "application/json")
	public ResultObject createDesktop(@RequestBody CommonRequestBean commonRequestBean) {
		// 开始创建桌面
		DesktopCreation desktopCreation = desktopService.createDesktop(commonRequestBean);
		log.info("createDesktop job_id = " + desktopCreation.getJobId());
		return ResultObject.success(desktopCreation.getJobId(), "正在创建桌面......");
	}

	/**
	 * 异步任务查询接口
	 *
	 * @param jobId
	 *            任务id
	 * @return
	 */
	@RequestMapping(value = "/queryJob/{jobId}", method = RequestMethod.GET)
	public ResultObject queryJob(@PathVariable(value = "jobId") String jobId) {
		JobBean job = desktopService.queryJob(jobId);
		log.info(job.toString());
		return ResultObject.success(job, "查询任务成功");
	}

	/**
	 * 删除桌面
	 *
	 * @param commonRequestBean
	 * @return
	 */
	@RequestMapping(value = "/deleteDesktop", method = RequestMethod.POST, consumes = "application/json")
	public ResultObject deleteDesktop(@RequestBody CommonRequestBean commonRequestBean) {

		// 校验参数
		CommonRequestBeanUtil.checkCommonRequestBean(commonRequestBean);
		CommonRequestBeanUtil.checkCommonRequestBeanForDelChgDesk(commonRequestBean);

		commonRequestBeanBuilder.buildBeanForDeleteDesktop(commonRequestBean);

		// 开始删除桌面
		return ResultObject.success(desktopService.deleteDesktop(commonRequestBean));
	}

	/**
	 * 修改桌面属性接口
	 *
	 * @param commonRequestBean
	 * @return
	 */
	@RequestMapping(value = "/changeDesktop", method = RequestMethod.POST, consumes = "application/json")
	public ResultObject changeDesktop(@RequestBody CommonRequestBean commonRequestBean) {

		// 校验参数
		CommonRequestBeanUtil.checkCommonRequestBean(commonRequestBean);
		CommonRequestBeanUtil.checkCommonRequestBeanForDelChgDesk(commonRequestBean);

		commonRequestBeanBuilder.buildBeanForChangeDesktop(commonRequestBean);

		// 开始修改桌面属性
		return ResultObject.success(desktopService.changeDesktop(commonRequestBean));
	}



	/**
	 * 修改桌面规格
	 *
	 * @param commonRequestBean
	 * @return
	 */
	@PostMapping(value = "changeDesktopSpec")
	public ResultObject changeDesktopSpec(@RequestBody CommonRequestBean commonRequestBean) {

		/*
		 * CommonRequestBeanUtil.checkCommonRequestBean(info); ResultObject resultObject
		 * = workspaceService.queryProductPackage(info); return resultObject;
		 */
		return null;

	}

    /**
     * 查询桌面详情
     *
     * @param commonRequestBean
     * @return ResultObject
     */
    @PostMapping(value = "queryDesktopDetail")
    public ResultObject queryDesktopDetail(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonQueryDesktopDetail(commonRequestBean);
        DesktopRequest desktopRequest = desktopService.queryDesktopDetail(commonRequestBean);
        return ResultObject.success(desktopRequest, "查询桌面详情成功!");

    }

    /**
     * 重启、启动、关闭桌面
     *
     * @param commonRequestBean
     * @return
     */

    @PostMapping(value = "operateDesktop")
    public ResultObject operateDesktop(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestBean(commonRequestBean);
        CommonRequestBeanUtil.checkOperateDesktop(commonRequestBean);
        String msg = desktopService.operateDesktop(commonRequestBean);
        return ResultObject.success(msg);

    }


    /**
     * 查询桌面列表、桌面详情列表
     */
    @PostMapping(value = "listOrDetailList")
    public ResultObject queryDesktopListOrDetail(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestProjectId(commonRequestBean);
        CommonRequestBeanUtil.checkDesktopListOrDetailType(commonRequestBean);
        DesktopRequest desktopRequest = desktopService.queryDesktopListOrDetail(commonRequestBean);
        return ResultObject.success(desktopRequest, "查询列表成功!");

    }


}
