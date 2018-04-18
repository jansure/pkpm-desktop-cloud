package com.cabr.pkpm.controller.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabr.pkpm.service.status.IStatusService;
import com.cabr.pkpm.utils.ResultObject;
import com.gateway.common.domain.CommonRequestBean;

@RestController
@RequestMapping("/status")
public class StatusController {
	
	@Autowired
	private IStatusService statusService;
    /**
     * 查询创建桌面时的 桌面创建状态
     * @param commonRequestBean
     * @return ResultObject
     */
	@PostMapping("/desktopStatus")
	public ResultObject desktopStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryDesktopStatus(commonRequestBean);
		if(response == null ){
			return ResultObject.failure("查询桌面状态失败,请重新尝试!");
		}
		return ResultObject.success(response);
		
	}
	/**
	 * 查询 重启、启动、关机、删除桌面 时的桌面状态
	 * @param commonRequestBean
	 * @return ResultObject
	 */
	@PostMapping("/operateStatus")
	public ResultObject operateStatus(@RequestBody CommonRequestBean commonRequestBean){
		
		String response = statusService.queryOperateStatus(commonRequestBean);
		if(response == null ){
			return ResultObject.failure("查询桌面操作状态失败,请重新尝试!");
		}
		return ResultObject.success(response);
		
	}
	
}
