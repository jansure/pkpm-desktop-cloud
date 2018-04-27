/**
 * 
 */
package com.gatewayserver.gatewayserver.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.PkpmProjectDef;
import com.gatewayserver.gatewayserver.service.GatewayServerParam;
import com.pkpm.httpclientutil.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 云平台下订单调用接口
 * @author yangpengfei
 * @time 2018年4月13日 上午11:01:41
 */
@RestController
@Slf4j
@RequestMapping("params")
public class GatewayServerParamController {

	@Resource
	private GatewayServerParam gatewayServerParam;
	
	/**
	 * 根据areaCode获取AdId和ProjectId
	 * @param areaCode
	 * @author yangpengfei
	 * @time 2018年4月13日 上午11:16:07
	 */
	@RequestMapping(value = "/getAdAndProject", method = RequestMethod.GET)
	public ResultObject getAdAndProject(String areaCode) {
		Map<String, String> adAndProject = gatewayServerParam.getAdAndProject(areaCode, null);
		
		if (StringUtils.isNotBlank(adAndProject.get("adId")) && StringUtils.isNotBlank(adAndProject.get("projectId"))) {
			log.info(adAndProject.get("adId"));
			log.info(adAndProject.get("projectId"));
			
			return ResultObject.success(adAndProject, "获取成功!");
		}
		
		return ResultObject.failure("没有获取到AdId和ProjectId！");
	}

	/**
	 * 根据projectId、areaCode获取ProjectDef
	 * @param projectId
	 * @param areaCode
	 * @author yangpengfei
	 * @date 2018/04/27
	 */
	@RequestMapping(value = "/getProjectDef", method = RequestMethod.GET)
	public ResultObject getProjectDef(String projectId, String areaCode) {
		PkpmProjectDef projectDef = gatewayServerParam.getProjectDef(projectId, areaCode);
		String jsonProjectDef = JsonUtil.serialize(projectDef);
		//return ResultObject.success(projectDef, "获取成功!");
		return ResultObject.success(jsonProjectDef, "获取成功!");
	}
}
