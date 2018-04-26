package com.pkpmdesktopcloud.desktopcloudbusiness.controller;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SubsDetailsService;
import com.pkpmdesktopcloud.desktopcloudbusiness.utils.ResponseResult;

@RestController
@RequestMapping("/subsdetails")
public class SubsDetailsController {
	
	@Autowired
	private SubsDetailsService subsDetailsService;
	/**
	 * 获取我的产品列表
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
//	public ResponseResult getList(int userID, int currentPage, int pageSize, HttpServletResponse response){
	public ResponseResult getList(@RequestBody Map<String, Integer> map, HttpServletResponse response){
		//允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResponseResult responseResult = new ResponseResult();
		//参数校验
		Preconditions.checkArgument(map == null || map.size() < 3, "参数个数不对！");
		Preconditions.checkArgument(map.get("userID") == null, "参数userID不能为空！");
		Preconditions.checkArgument(map.get("currentPage") == null, "参数currentPage不能为空！");
		Preconditions.checkArgument(map.get("pageSize") == null, "参数pageSize不能为空！");
	    PageBean<MyProduct> pageBean = subsDetailsService.showList(map.get("userID"), map.get("currentPage"), map.get("pageSize"));
		
	    if(pageBean != null && pageBean.getItems() != null) {
	    	responseResult.set("成功", 1, pageBean);
		}else {
			responseResult.set("失败", 0);
		}
	    
	    return responseResult;
	}
	
	
	
}
