package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudProductDefService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author yangpengfei
 * @date 2017/12/22
 *
 */
@RestController
@Api(description ="产品功能")
@Slf4j
@RequestMapping(value = "/product")
public class ProductController {
	
	@Resource
	private PkpmCloudProductDefService productService;
	
	@Resource
	private PkpmCloudComponentDefService componentDefService;
	
	/**
	 * 返回默认的产品套餐配置列表
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation("获取产品套餐配置列表")
	@RequestMapping(value = "/product-buy", method = RequestMethod.POST)
	public ResultObject getProductTypeList(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> map = new HashMap<String, Object>(16);
		// 产品套餐类型列表，默认选项为全家桶类型
		List<PkpmCloudProductDef> productTypeList = productService.getProductTypeList();
		map.put(SysConstant.BUY_TYPE, productTypeList);
		
		// 配置类型列表
		List<PkpmCloudComponentDef> componentDefList = componentDefService.getList();
		if(componentDefList != null && componentDefList.size() > 0) {
			Map<Integer, List<PkpmCloudComponentDef>> componentDefMap = componentDefList.stream().collect(Collectors.groupingBy(PkpmCloudComponentDef::getComponentType));
			map.put("componentDefMap",componentDefMap);
		}
				
		log.debug("map ：" + map);
		return ResultObject.success(map);
	}

}
