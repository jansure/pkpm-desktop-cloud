package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;
import com.pkpmdesktopcloud.redis.RedisCache;

//fixme
@Slf4j
@RestController
@RequestMapping(value = "sysconfig")
public class SystemConfigContoller {
	// fixme

	@Resource
	private PkpmSysConfigService sysConfigService;

	@ResponseBody
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
