package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.Base64Util;
import com.desktop.utils.FileUtil;
import com.desktop.utils.SmsUtil;
import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.FileServerResponse;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.ProductService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.WorkOrderService;
import com.pkpmdesktopcloud.redis.RedisCache;

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
	
	private static final String ALL_NAVIGATION_ID = "allNavigation";
	
	@Resource
	private ProductService productService;
	
	@Resource
	private WorkOrderService workOrderService;
	
	/**
	 * 获取全部导航列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subProducts", method = RequestMethod.GET)
	public List<PkpmCloudNavigation> getNavigation(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		RedisCache cache = new RedisCache(ALL_NAVIGATION_ID);
		List<PkpmCloudNavigation> listNav = (List<PkpmCloudNavigation>)cache.getObject("all");
		
		// 若存在Redis缓存，从缓存中读取
		if (listNav != null) {

			return listNav;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		listNav = productService.getNavByPid(SysConstant.NAVIGATION_ID);
		// 写入Redis缓存
		cache.putObject("all", listNav);
		return listNav;
	}

	//fixme 移到其他资源类里边
	/**
	 * 获取帮助文档、使用教程、下载云桌面客户端
	 * 
	 * @param filename
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/downloads", method = RequestMethod.GET)
	public ResultObject getHelp(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		boolean isOnLine = false;
		//filename = "BIM协同设计管理云平台.pdf";
		
		Preconditions.checkArgument(StringUtils.isBlank(filename), "文件名不能为空");
		
		log.debug("filename:" + filename + "; isOnLine:" + isOnLine);
		
		// 文件系统路径
		String fileUrl = productService.getSysValue(SysConstant.FILE_BASE_URL);

		String json = FileUtil.loadJson(fileUrl);
		// System.out.println(json); //检测是否正确获得
		
		FileServerResponse fileServerResponse = JsonUtil.deserialize(json, FileServerResponse.class);
		if (fileServerResponse != null && fileServerResponse.getFiles() != null) {
		// 遍历已有的文件列表，看文件名是否存在
			try {
				if (fileServerResponse.getFiles().contains(filename)) {
					// 若存在该文件名，则开启下载；若不存在，抛出异常
					String filePath = fileUrl + "/" + filename;
					FileUtil.downloadFile(filePath, isOnLine, request, response);
					log.debug("FileUtil.downloadFile:" + filePath);
					return ResultObject.success(filePath, "下载成功！");
				}
				
				return ResultObject.failure("该文件不存在！");
				
			} catch (Exception e) {
				
				log.error(e.getMessage());
				
			}
		}
		
		String msg = "<" + filename + ">下载失败！";
		return ResultObject.failure(msg);
	}

	/**
	 * 获取法律条款说明
	 * 
	 * @param filename
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/legalTerms", method = RequestMethod.GET)
	public ResultObject getLegalTerms(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "法律条款声明.txt";
//		Preconditions.checkArgument(StringUtils.isBlank(filename), "文件名不能为空");
		
		String url = productService.getSysValue(SysConstant.FILE_BASE_URL) + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			return ResultObject.success(termsJson, "获取成功");
		} 
		
		return ResultObject.failure("获取失败");
	}
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
		List<Map<String, Object>> componentTypeList = productService.getComponentTypeList();
		for (Map<String, Object> component : componentTypeList) {
			Integer componentType = (Integer) component.get("componentType");
			// 每一种配置类型对应其包含的配置项列表
			map.put(componentType.toString(), productService.getConfigByComponentType(componentType));
		}
		log.debug("map ：" + map);
		return ResultObject.success(map);
	}
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
		
		List<Integer> compTypeList = productService.getCompTypeList(productType);
		log.debug("compTypeList ：" + compTypeList);
		Map<Integer, List<ComponentVO>> componentsMap = new LinkedHashMap<Integer, List<ComponentVO>>();
		// 每种产品配置类别中的配置项
		for (Integer compType : compTypeList) {
			List<ComponentVO> components = productService.getComponentByPid(productType, compType);
			componentsMap.put(compType, components);
		}
		
		return ResultObject.success(componentsMap);
	}
	/**
	 * 根据用户手机号及工单号发短信通知用户云桌面开户信息
	 * @param userMobileNumber
	 * @param subsId
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
	
	/**
	 * 获取使用教程说明
	 * @author yangpengfei
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public ResultObject getManual(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "使用教程说明.txt";
		String url = productService.getSysValue(SysConstant.FILE_BASE_URL) + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			return ResultObject.success(termsJson, "获取成功");
		}
		
		return ResultObject.failure("获取失败");
	}
	
}
