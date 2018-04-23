package com.cabr.pkpm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.service.IWorkOrderService;
import com.cabr.pkpm.utils.ResponseResult;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {
	
	@Autowired
	private IWorkOrderService workOrderService;
	
	protected ResponseResult result=new ResponseResult();  
	
	@RequestMapping(value = "/findWorkOrderList", method = RequestMethod.POST)
	public ResponseResult findWorkOrderList(  @RequestParam(required=true,defaultValue="1")Integer currentPage,
		@RequestParam(required=true,defaultValue="10") Integer pageSize, HttpSession session){
		UserInfo userInfo = (UserInfo) session.getAttribute("userCache");
		/*if(userInfo == null){
			this.result.set("请您先登录", 0);
			return this.result;
		}
		if(userInfo.getUserType() != 1){
			this.result.set("对不起,您无权限查看", 0);
			return this.result;
		}*/
		
		return workOrderService.findWorkOrderList(currentPage,pageSize);
	}
	
	@RequestMapping(value = "/updateWorkOrder", method = RequestMethod.POST)
	public ResponseResult updateWorkOrder(Integer id,Long workId){
		/*if(userInfo == null){
			this.result.set("请您先登录", 0);
			return this.result;
		}
		if(userInfo.getUserType() != 1){
			this.result.set("对不起,您无权限查看", 0);
			return this.result;
		}*/
		
		return workOrderService.updateWorkOrder(id,workId);
	}
	
}
