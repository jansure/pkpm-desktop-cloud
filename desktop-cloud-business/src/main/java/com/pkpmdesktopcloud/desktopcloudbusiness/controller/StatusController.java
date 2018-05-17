package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.StatusService;

@RestController
@Api(description ="桌面操作")
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
	private StatusService statusService;

	@ApiOperation("获取桌面状态")
	@PostMapping("/desktopStatus")
	public ResultObject desktopStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryDesktopStatus(commonRequestBean);
		
		if(StringUtils.isNotEmpty(response)){
			
			return ResultObject.success(response, "查询桌面状态成功!");
		}
		return ResultObject.failure("查询桌面状态失败,请重新尝试!");
		
	}
	/**
	 * 查询 重启、启动、关机、删除桌面 时的桌面状态
	 * @param commonRequestBean
	 * @return ResultObject
	 */
	@ApiOperation("获取桌面操作状态")
	@PostMapping("/operateStatus")
	public ResultObject operateStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryOperateStatus(commonRequestBean);
		if(StringUtils.isNotEmpty(response)){
			
			return ResultObject.success(response, "查询桌面状态成功!");
		}
		
		return ResultObject.failure("查询桌面状态失败,请重新尝试!");
	}
	
}
