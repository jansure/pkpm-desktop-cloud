package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudNavigationService;
import com.pkpmdesktopcloud.redis.RedisCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(description ="获取导航栏内容")
@RestController
@RequestMapping(value = "/navigator")
public class NavigatorController {
	
	@Resource
	private PkpmCloudNavigationService pkpmCloudNavigationService;
	
	/**
	 * 获取全部导航列表
	 * 
	 * @return
	 */
	@ResponseBody
	@ApiOperation("获取全部导航列表")
	@RequestMapping(value = "/subProducts", method = RequestMethod.GET)
	public ResultObject getNavigation(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		List<PkpmCloudNavigation> listNav = pkpmCloudNavigationService.getNavTree();
		return ResultObject.success(listNav);
	}

}
