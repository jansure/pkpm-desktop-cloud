package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.desktop.utils.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ComponentDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ProductDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.Navigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ProductInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.ProductService;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	@Resource
	private ComponentDAO componentDAO;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public List<ProductInfo> getProductByParentId(String productType) {
		String str = stringRedisTemplate.opsForValue().get("product:" + productType);
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(str)) {
			List<ProductInfo> productInfo = JSON.parseArray(str, ProductInfo.class);
			return productInfo;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			List<ProductInfo> productInfo = productDAO.getProductByType(StringUtil.stringToInt(productType));
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("product:" + productType, JSON.toJSONString(productInfo));
			return productInfo;
		}
	}

	@Override
	public String getSysValue(String key) {
		String keystr = stringRedisTemplate.opsForValue().get("sysconfig:" + key);
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(keystr)) {
			SysConfig sysConfig = JSON.parseObject(keystr, SysConfig.class);
			return sysConfig.getValue();
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			SysConfig sysConfig = productDAO.getSysConfig(key);
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("sysconfig:" + key, JSON.toJSONString(sysConfig));
			return sysConfig.getValue();
		}
	}

	@Override
	public List<Navigation> getNavByPid(Integer parentNavId) {
		List<Navigation> list = productDAO.getNavByPid(parentNavId);
		return list;
	}

	@Override
	public List<ComponentVO> getComponentByPid(Integer productType, Integer componentType) {
		List<ComponentVO> componentInfo = productDAO.getComponentByPid(productType, componentType);
		return componentInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProductTypeList() {
		String str = stringRedisTemplate.opsForValue().get("productTypeList");
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(str)) {
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Object>> productTypeList = new ArrayList<Map<String, Object>>();
			try {
				productTypeList = objectMapper.readValue(str, List.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return productTypeList;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			List<Map<String, Object>> productTypeList = productDAO.getProductTypeList();
			for (Map<String, Object> productType : productTypeList) {
				// 设置“全家桶类型”为默认套餐
				if (SysConstant.PRODUCT_TYPE_ALL.equals(productType.get("productType").toString())) {
					productType.put("isDefault", SysConstant.IS_DEFAULT_YES);
				}
			}
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("productTypeList", JSON.toJSONString(productTypeList));
			return productTypeList;
		}
	}

	@Override
	public List<Integer> getCompTypeList(Integer productType) {
		List<Integer> compTypeList = productDAO.getCompTypeList(productType);
		return compTypeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getComponentTypeList() {
		String str = stringRedisTemplate.opsForValue().get("componentTypeList");
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(str)) {
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Object>> componentTypeList = new ArrayList<Map<String, Object>>();
			try {
				componentTypeList = objectMapper.readValue(str, List.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return componentTypeList;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			List<Map<String, Object>> componentTypeList = componentDAO.getComponentTypeList();
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("componentTypeList", JSON.toJSONString(componentTypeList));
			return componentTypeList;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getConfigByComponentType(Integer componentType) {
		String str = stringRedisTemplate.opsForValue().get("componentList:" + componentType);
		// 若存在Redis缓存，从缓存中读取
		if (StringUtils.isNotBlank(str)) {
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Object>> configList = new ArrayList<Map<String, Object>>();
			try {
				configList = objectMapper.readValue(str, List.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return configList;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			List<Map<String, Object>> configList = componentDAO.getConfigByComponentType(componentType);
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("componentList:" + componentType, JSON.toJSONString(configList));
			return configList;
		}
	}

}
