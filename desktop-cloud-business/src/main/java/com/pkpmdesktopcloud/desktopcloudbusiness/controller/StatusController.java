package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.common.domain.CommonRequestBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.StatusService;
import com.pkpmdesktopcloud.desktopcloudbusiness.utils.ResponseResult;

@RestController
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
	private StatusService statusService;
    /**
     * 查询创建桌面时的 桌面创建状态
     * @param commonRequestBean
     * @return ResultObject
     */
	protected ResponseResult result = new ResponseResult();
	
	@PostMapping("/desktopStatus")
	public ResponseResult desktopStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryDesktopStatus(commonRequestBean);
		
		if(response == null ){
			this.result.set("查询桌面状态失败,请重新尝试!", 0);
			return this.result;
		}
		this.result.set("查询桌面状态成功!",1 , response);
		return this.result;
		
	}
	/**
	 * 查询 重启、启动、关机、删除桌面 时的桌面状态
	 * @param commonRequestBean
	 * @return ResultObject
	 */
	@PostMapping("/operateStatus")
	public ResponseResult operateStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryOperateStatus(commonRequestBean);
		if(response == null ){
			//return ResultObject.failure("查询桌面操作状态失败,请重新尝试!");
			this.result.set("查询桌面操作状态失败,请重新尝试!", 0);
			return this.result;
		}
		this.result.set("查询桌面操作状态成功!", 1, response);
		return this.result;
		
	}
	
}
