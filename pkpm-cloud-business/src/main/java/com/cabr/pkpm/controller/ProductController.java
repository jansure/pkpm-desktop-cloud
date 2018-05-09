package com.cabr.pkpm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cabr.pkpm.constants.SysConstant;
import com.cabr.pkpm.entity.product.ComponentVO;
import com.cabr.pkpm.entity.product.Navigation;
import com.cabr.pkpm.service.IProductService;
import com.cabr.pkpm.utils.Base64Utils;
import com.cabr.pkpm.utils.FileUtil;
import com.cabr.pkpm.utils.ResponseResult;
import com.cabr.pkpm.utils.StringUtil;
import com.cabr.pkpm.utils.sdk.ClientDemo;

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
	@Autowired
	private IProductService productService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	protected ResponseResult result = new ResponseResult();

	/**
	 * 获取全部导航列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subProducts", method = RequestMethod.GET)
	public List<Navigation> getNavigation(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String str = stringRedisTemplate.opsForValue().get("allNavigation");
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(str)) {
			List<Navigation> listNav = JSON.parseArray(str, Navigation.class);
			return listNav;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			List<Navigation> listNav = productService.getNavByPid(SysConstant.NAVIGATION_ID);
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("allNavigation", JSON.toJSONString(listNav));
			return listNav;
		}
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
	public ResponseResult getHelp(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		boolean isOnLine = false;
		//filename = "BIM协同设计管理云平台.pdf";
		if (StringUtils.isBlank(filename)) {
			this.result.set("文件名不能为空", 0);
			return this.result;
		}
		log.debug("filename:" + filename + "; isOnLine:" + isOnLine);
		// 文件系统路径
		String fileUrl = productService.getSysValue(SysConstant.FILE_BASE_URL);

		String json = FileUtil.loadJson(fileUrl);
		// System.out.println(json); //检测是否正确获得
		JSONObject jsonObject = JSONObject.parseObject(json);
		// System.out.println(jsonObject.get("files"));
		JSONArray array = null;
		if (jsonObject.get("files") != null) {
			array = JSON.parseArray(jsonObject.get("files").toString());
			// 遍历已有的文件列表，看文件名是否存在
			for (int i = 0; i < array.size(); i++) {
				try {
					if (filename.equals(array.getString(i))) {
						// 若存在该文件名，则开启下载；若不存在，抛出异常
						String filePath = fileUrl + "/" + filename;
						FileUtil.downloadFile(filePath, isOnLine, request, response);
						log.debug("FileUtil.downloadFile:" + filePath);
						this.result.set("下载成功！", 1, filePath);
					} else {
						this.result.set("该文件不存在！", 0);
					}
				} catch (Exception e) {
					String msg = "<" + array.getString(i) + ">下载失败！";
					log.error(e.getMessage());
					this.result.set(msg, 0);
				}
			}
		} else {
			this.result.set("该文件不存在！", 0);
		}
		return this.result;
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
	public ResponseResult getLegalTerms(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "法律条款声明.txt";
		if (StringUtils.isBlank(filename)) {
			this.result.set("文件名不能为空", 0);
			return this.result;
		}
		String url = productService.getSysValue(SysConstant.FILE_BASE_URL) + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			this.result.set("获取成功", 1, "1", termsJson);
		} else {
			this.result.set("获取失败", 0);
		}
		return this.result;
	}
	/**
	 * 返回默认的产品套餐配置列表
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/product-buy", method = RequestMethod.POST)
	public Map<String, List<Map<String, Object>>> getProductTypeList(HttpServletResponse response) {
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
		return map;
	}
	/**
	 * 根据产品类型id获取自动配置的components
	 * 
	 * @param productType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subComponents", method = RequestMethod.POST)
	public Map<Integer, List<ComponentVO>> getComponentByProductType(@RequestBody Map<String,String> map, HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String productType = map.get("productType");
		List<Integer> compTypeList = productService.getCompTypeList(StringUtil.stringToInt(productType));
		log.debug("compTypeList ：" + compTypeList);
		Map<Integer, List<ComponentVO>> componentsMap = new LinkedHashMap<Integer, List<ComponentVO>>();
		// 每种产品配置类别中的配置项
		for (Integer compType : compTypeList) {
			List<ComponentVO> components = productService.getComponentByPid(productType, compType.toString());
			componentsMap.put(compType, components);
		}
		return componentsMap;
	}
	/**
	 * 根据用户手机号及工单号发短信通知用户云桌面开户信息
	 * @param userMobileNumber
	 * @param subsId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendClientMessage", method = RequestMethod.POST)
	public ResponseResult sendClientMessage(String userMobileNumber, String workId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		if (StringUtils.isBlank(userMobileNumber)||StringUtils.isBlank(workId)) {
			this.result.set("手机号/工单号不能为空", 0);
			log.debug(this.result.getMessage());
			return this.result;
		}
		try {
			List<Map<String, String>> sendList = productService.getClientInfo(userMobileNumber, StringUtil.stringToLong(workId));
			if (sendList.size()==0) {
				this.result.set("查无此手机号或工单号", 0);
			} else {
				Map<String, String> map = sendList.get(0);
				String password = Base64Utils.stringFromB64(map.get("userLoginPassword").toString());
				String message = "您好，已成功为您开通构力云桌面，服务器地址是" + map.get("hostIp") + "，登录用户名是" + map.get("userName") + "，登录密码是" + password + "，请注意查收！";
				ClientDemo clientDemo = new ClientDemo();
				clientDemo.smsPublish(userMobileNumber, message);
				this.result.set("发送短信成功", 1);
				log.debug(this.result.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("发送短信产生异常:" + e);
			this.result.set("发送短信失败", 0);
		}

		return this.result;
		
	}
	
	/**
	 * 获取使用教程说明
	 * @author yangpengfei
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public ResponseResult getManual(HttpServletResponse response) {
		// 允许跨域调用
		response.setHeader("Access-Control-Allow-Origin", "*");
		String filename = "使用教程说明.txt";
		String url = productService.getSysValue(SysConstant.FILE_BASE_URL) + "/" + filename;
		String termsJson = FileUtil.getHttpResponse(url);
		if (!StringUtils.isEmpty(termsJson)) {
			this.result.set("获取成功", 1, "1", termsJson);
		} else {
			this.result.set("获取失败", 0);
		}
		return this.result;
	}
}
