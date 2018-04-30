package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.WorkOrder;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.WorkOrderService;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {
	
	@Autowired
	private WorkOrderService workOrderService;
	
	@RequestMapping(value = "/findWorkOrderList", method = RequestMethod.POST)
	public ResultObject findWorkOrderList(  @RequestParam(required=true,defaultValue="1")Integer currentPage,
		@RequestParam(required=true,defaultValue="10") Integer pageSize, HttpSession session){
	
		PageBean<WorkOrder> pageData = workOrderService.findWorkOrderList(currentPage,pageSize);

	    if(pageData != null && pageData.getItems() != null) {

	    	return ResultObject.success(pageData, "成功");
		}
	    
	    return ResultObject.failure("失败");
	}
	
	@RequestMapping(value = "/updateWorkOrder", method = RequestMethod.POST)
	public ResultObject updateWorkOrder(Integer id,Long workId){
		
	    Integer count = workOrderService.updateWorkOrder(id,workId);
	    if(count != null && count > 2) {
	    	
	    	return ResultObject.success("成功");
		}
	    
		return ResultObject.failure("失败");
		
	}
	
}
