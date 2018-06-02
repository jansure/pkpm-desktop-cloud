package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;
import com.pkpmdesktopcloud.redis.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//fixme
@Slf4j
@RestController
@Api(description ="系统配置信息")
@RequestMapping(value = "/sysconfig")
public class SystemConfigContoller {
	// fixme

	@Resource
	private PkpmSysConfigService sysConfigService;

	@ResponseBody
	@ApiOperation("获取系统信息")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResultObject test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 若存在Redis缓存，从缓存中读取
		RedisCache cache = new RedisCache("sysConfigKey");
		PkpmSysConfig pkpmSysConfig = (PkpmSysConfig)cache.getObject(SysConstant.FILE_BASE_URL);
		if(pkpmSysConfig != null) {
			log.info("缓存中数据");
			return ResultObject.success(pkpmSysConfig);
		}
	
		//不存在从数据库取
		pkpmSysConfig = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL);
		Preconditions.checkNotNull(pkpmSysConfig, "配置信息不存在!");
		log.info("数据库中数据");
		
		//放入缓存
		cache.putObject(SysConstant.FILE_BASE_URL, pkpmSysConfig);
		return ResultObject.success(pkpmSysConfig);
	}

}
