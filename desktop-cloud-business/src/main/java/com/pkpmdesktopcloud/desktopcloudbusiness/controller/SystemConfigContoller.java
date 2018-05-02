package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmSysConfigService;

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
		PkpmSysConfig pkpmSysConfig = sysConfigService.getPkpmSysConfigByKey(SysConstant.FILE_BASE_URL);
		return ResultObject.success(pkpmSysConfig);
	}

}
