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
	
	private static final String ALL_NAVIGATION_ID = "allNavigation";
	
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
	public List<PkpmCloudNavigation> getNavigation(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		RedisCache cache = new RedisCache(ALL_NAVIGATION_ID);
		List<PkpmCloudNavigation> listNav = (List<PkpmCloudNavigation>)cache.getObject("all");
		
		// 若存在Redis缓存，从缓存中读取
		if (listNav != null) {

			return listNav;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		listNav = pkpmCloudNavigationService.getNavByParentId(SysConstant.NAVIGATION_ID);
		// 写入Redis缓存
		cache.putObject("all", listNav);
		return listNav;
	}

	//fixme 移到其他资源类里边
}
