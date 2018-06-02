package com.pkpmdesktopcloud.desktopcloudbusiness.controller;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubsDetailsService;

@RestController
@Api(description ="订单详情操作")
@RequestMapping("/subsdetails")
public class SubsDetailsController {
	
	@Autowired
	private PkpmCloudSubsDetailsService subsDetailsService;
	/**
	 * 获取我的产品列表
	 */
	@ApiOperation("获取我的产品列表")
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public ResultObject getList(Integer userID, Integer currentPage, Integer pageSize, HttpServletResponse response){
		//允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		//参数校验
		Preconditions.checkNotNull(userID, "参数userID不能为空！");
		Preconditions.checkNotNull(currentPage, "参数currentPage不能为空！");
		Preconditions.checkNotNull(pageSize, "参数pageSize不能为空！");
		
	    PageBean<MyProduct> pageBean = subsDetailsService.showList(userID, currentPage, pageSize);
		
	    if(pageBean != null && pageBean.getItems() != null) {
	    	return ResultObject.success(pageBean);
		}
	    
	    return ResultObject.failure("查询失败");
	}
	
	
	
}
