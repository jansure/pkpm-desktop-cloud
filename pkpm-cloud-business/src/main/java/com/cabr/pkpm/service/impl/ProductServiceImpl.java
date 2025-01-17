package com.cabr.pkpm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cabr.pkpm.constants.SysConstant;
import com.cabr.pkpm.entity.SysConfig;
import com.cabr.pkpm.entity.product.ComponentVO;
import com.cabr.pkpm.entity.product.Navigation;
import com.cabr.pkpm.entity.product.ProductInfo;
import com.cabr.pkpm.mapper.product.ProductMapper;
import com.cabr.pkpm.service.IProductService;
import com.cabr.pkpm.utils.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class ProductServiceImpl implements IProductService {
	@Resource
	private ProductMapper productMapper;

	@Resource
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
			List<ProductInfo> productInfo = productMapper.getProductByParentId(StringUtil.stringToInt(productType));
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
			SysConfig sysConfig = productMapper.getSysConfig(key);
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("sysconfig:" + key, JSON.toJSONString(sysConfig));
			return sysConfig.getValue();
		}
	}

	@Override
	public List<Navigation> getNavByPid(Integer parentNavId) {
		List<Navigation> list = productMapper.getNavByPid(parentNavId);
		return list;
	}

	@Override
	public List<ComponentVO> getComponentByPid(String productType, String componentType) {
		List<ComponentVO> componentInfo = productMapper.getComponentByPid(StringUtil.stringToInt(productType), StringUtil.stringToInt(componentType));
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
			List<Map<String, Object>> productTypeList = productMapper.getProductTypeList();
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
		List<Integer> compTypeList = productMapper.getCompTypeList(productType);
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
			List<Map<String, Object>> componentTypeList = productMapper.getComponentTypeList();
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
			List<Map<String, Object>> configList = productMapper.getConfigByComponentType(componentType);
			// 写入Redis缓存
			stringRedisTemplate.opsForValue().set("componentList:" + componentType, JSON.toJSONString(configList));
			return configList;
		}
	}

	@Override
	public List<Map<String, String>> getClientInfo(String userMobileNumber, Long workId) {
		List<Map<String, String>> clientInfoList = productMapper.getClientInfo(userMobileNumber, workId);
		return clientInfoList;
	}

}
