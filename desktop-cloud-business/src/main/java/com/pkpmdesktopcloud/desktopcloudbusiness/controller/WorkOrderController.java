package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.WorkOrderService;
import com.pkpmdesktopcloud.desktopcloudbusiness.utils.ResponseResult;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {
	
	@Autowired
	private WorkOrderService workOrderService;
	
	protected ResponseResult result=new ResponseResult();  
	
	@RequestMapping(value = "/findWorkOrderList", method = RequestMethod.POST)
	public ResponseResult findWorkOrderList(  @RequestParam(required=true,defaultValue="1")Integer currentPage,
		@RequestParam(required=true,defaultValue="10") Integer pageSize, HttpSession session){
		ResponseResult responseResult = new ResponseResult();
	
		PageBean<WorkOrder> pageData = workOrderService.findWorkOrderList(currentPage,pageSize);

	    if(pageData != null && pageData.getItems() != null) {
	    	responseResult.set("成功", 1, pageData);
		}else {
			responseResult.set("失败", 0);
		}
	    
		return responseResult;
	}
	
	@RequestMapping(value = "/updateWorkOrder", method = RequestMethod.POST)
	public ResponseResult updateWorkOrder(Integer id,Long workId){
		
		
		ResponseResult responseResult = new ResponseResult();
		
	    Integer count = workOrderService.updateWorkOrder(id,workId);
	    if(count != null && count > 2) {
	    	responseResult.set("成功", 1);
		}else {
			responseResult.set("失败", 0);
		}
	    
		return responseResult;
		
	}
	
}
