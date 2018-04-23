package com.gateway.cloud.business.controller;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.cloud.business.service.SubsDetailsService;
import com.gateway.cloud.business.utils.ResponseResult;

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
	public ResponseResult getList(@RequestBody Map<String, String> map, HttpServletResponse response){
		//允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		return subsDetailsService.showList(new Integer(map.get("userID")),new Integer(map.get("currentPage")),new Integer(map.get("pageSize")));
	}
	
	
	
}
