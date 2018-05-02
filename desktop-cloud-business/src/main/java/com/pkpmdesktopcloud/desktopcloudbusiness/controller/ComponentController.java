package com.pkpmdesktopcloud.desktopcloudbusiness.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "component")
public class ComponentController {
	
	@Resource
	private PkpmCloudComponentDefService componentDefService;
	
	/**
	 * 根据产品类型id获取自动配置的components
	 * 
	 * @param productType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subComponents", method = RequestMethod.POST)
	public ResultObject getComponentByProductType(Integer productType, HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		List<ComponentVO> componentVOList = componentDefService.getComponentListByProductType(productType);
		
		Map<Integer, List<ComponentVO>> componentsMap = new LinkedHashMap<Integer, List<ComponentVO>>();
		
		// 每种产品配置类别中的配置项
		if(componentVOList != null && componentVOList.size() > 0) {
			componentsMap = componentVOList.stream().collect(Collectors.groupingBy(ComponentVO::getComponentType));
		}
		
		return ResultObject.success(componentsMap);
	}
	
}
