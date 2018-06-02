package com.cabr.pkpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.service.IStatusService;
import com.cabr.pkpm.service.IUserService;
import com.cabr.pkpm.utils.ResponseResult;
import com.cabr.pkpm.utils.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.StatusObject;

@RestController
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IUserService userService;
    /**
     * 查询创建桌面时的 桌面创建状态
     * @param commonRequestBean
     * @return ResultObject
     */
	protected ResponseResult result = new ResponseResult();
	
	@PostMapping("/desktopStatus")
	public ResponseResult desktopStatus(@RequestBody CommonRequestBean commonRequestBean){
		StatusObject statusObject = null ;
		try {
			statusObject = statusService.queryDesktopStatus(commonRequestBean);
		} catch (Exception e) {
			e.printStackTrace();
			this.result.set("查询桌面状态失败,请重新尝试!",0 , statusObject);
			return this.result;
		}
		
		if(statusObject == null ){
			this.result.set("查询桌面状态失败,请重新尝试!",0 , statusObject);
			return this.result;
		
		}
		
		UserInfo user = userService.findUser(commonRequestBean.getUserId());
		String userName = user.getUserName();
		statusObject.setUsername(userName);
		
		this.result.set("查询桌面状态成功!",1 , statusObject);
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
