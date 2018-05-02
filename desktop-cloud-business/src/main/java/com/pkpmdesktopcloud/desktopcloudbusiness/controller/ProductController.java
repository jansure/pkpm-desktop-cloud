package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.Base64Util;
import com.desktop.utils.SmsUtil;
import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author yangpengfei
 * @date 2017/12/22
 *
 */
@RestController
@Slf4j
@RequestMapping(value = "/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@Resource
	private PkpmCloudComponentDefService componentDefService;
	
	/**
	 * 返回默认的产品套餐配置列表
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/product-buy", method = RequestMethod.POST)
	public ResultObject getProductTypeList(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>(16);
		// 产品套餐类型列表，默认选项为全家桶类型
		List<Map<String, Object>> productTypeList = productService.getProductTypeList();
		map.put(SysConstant.BUY_TYPE, productTypeList);
		// 配置类型列表
		List<Map<String, Object>> componentTypeList = componentDefService.getComponentTypeList();
		for (Map<String, Object> component : componentTypeList) {
			Integer componentType = (Integer) component.get("componentType");
			// 每一种配置类型对应其包含的配置项列表
			map.put(componentType.toString(), productService.getConfigByComponentType(componentType));
		}
		log.debug("map ：" + map);
		return ResultObject.success(map);
	}

	/**
	 * 根据用户手机号及工单号发短信通知用户云桌面开户信息
	 * @param userMobileNumber
	 * @param
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendClientMessage", method = RequestMethod.POST)
	public ResultObject sendClientMessage(String userMobileNumber, Long workId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Preconditions.checkArgument(StringUtils.isBlank(userMobileNumber), "手机号不能为空");
		Preconditions.checkNotNull(workId, "工单号不能为空");
		
		try {
			List<Map<String, String>> sendList = workOrderService.getClientInfo(userMobileNumber, workId);
			Preconditions.checkArgument(sendList.size()==0, "查无此手机号或工单号");
		
			Map<String, String> map = sendList.get(0);
			String password = Base64Util.stringFromB64(map.get("userLoginPassword").toString());
			String message = "您好，已成功为您开通构力云桌面，服务器地址是" + map.get("hostIp") + "，登录用户名是" + map.get("userName") + "，登录密码是" + password + "，请注意查收！";
			SmsUtil smsUtil = new SmsUtil();
			smsUtil.smsPublish(userMobileNumber, message);
			
			return ResultObject.success("发送短信成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信产生异常:" + e);
		}

		return ResultObject.failure("发送短信失败");
		
	}
	
}
