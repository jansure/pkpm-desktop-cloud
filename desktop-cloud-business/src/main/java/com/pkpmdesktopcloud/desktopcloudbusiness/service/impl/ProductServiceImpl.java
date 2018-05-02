package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.ProductService;
import com.pkpmdesktopcloud.redis.RedisCache;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	
	private static final String PRODUCT_ID = "product";
	
	private static final String SYS_CONFIG_ID = "sysconfig";
	
	private static final String PRODUCT_TYPE_LIST_ID = "productTypeList";
	
	private static final String COMPOENT_TYPE_LIST_ID = "componentTypeList";
	
	private static final String ALL_TYPE_REDIS_KEY = "allTypeRedisKey";
	
	@Autowired
	private PkpmCloudProductDefDAO productDAO;
	
	@Resource
	private PkpmCloudComponentDefDAO componentDAO;

	@Override
	public List<PkpmCloudProductDef> getProductByType(Integer productType) {
		
		// 若存在Redis缓存，从缓存中读取
		RedisCache cache = new RedisCache(PRODUCT_ID);
		List<PkpmCloudProductDef> productInfo = (List<PkpmCloudProductDef>)cache.getObject(productType);
		if(productInfo != null) {
			
			return productInfo;
		}
			
		// 若不存在对应的Redis缓存，从数据库查询
		productInfo = productDAO.getProductByType(productType);
		// 写入Redis缓存
		cache.putObject(PRODUCT_ID, productInfo);
		return productInfo;
	}


	@Override
	public List<ComponentVO> getComponentByPid(Integer productType, Integer componentType) {
		List<ComponentVO> componentInfo = productDAO.getComponentByPid(productType, componentType);
		return componentInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getProductTypeList() {
		RedisCache cache = new RedisCache(PRODUCT_TYPE_LIST_ID);
		List<Map<String, Object>> productTypeList = (List<Map<String, Object>>)cache.getObject(ALL_TYPE_REDIS_KEY);
		
		// 若存在Redis缓存，从缓存中读取
		if (productTypeList != null) {
			return productTypeList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		productTypeList = productDAO.getProductTypeList();
		for (Map<String, Object> productType : productTypeList) {
			// 设置“全家桶类型”为默认套餐
			if (SysConstant.PRODUCT_TYPE_ALL.equals(productType.get("productType").toString())) {
				productType.put("isDefault", SysConstant.IS_DEFAULT_YES);
			}
		}
		
		// 写入Redis缓存
		cache.putObject(ALL_TYPE_REDIS_KEY, productTypeList);
		return productTypeList;
	}

	@Override
	public List<Integer> getCompTypeList(Integer productType) {
		List<Integer> compTypeList = productDAO.getCompTypeList(productType);
		return compTypeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getComponentTypeList() {
		
		RedisCache cache = new RedisCache(COMPOENT_TYPE_LIST_ID);
		List<Map<String, Object>> componentTypeList = (List<Map<String, Object>>)cache.getObject(ALL_TYPE_REDIS_KEY);
		
		// 若存在Redis缓存，从缓存中读取
		if (componentTypeList != null) {
			
			return componentTypeList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		componentTypeList = componentDAO.getComponentTypeList();
		
		// 写入Redis缓存
		cache.putObject(ALL_TYPE_REDIS_KEY, componentTypeList);
		return componentTypeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getConfigByComponentType(Integer componentType) {
		RedisCache cache = new RedisCache(COMPOENT_TYPE_LIST_ID);
		List<Map<String, Object>> componentTypeList = (List<Map<String, Object>>)cache.getObject(componentType);
		
		// 若存在Redis缓存，从缓存中读取
		if (componentTypeList != null) {
			
			return componentTypeList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		componentTypeList = componentDAO.getConfigByComponentType(componentType);
		
		// 写入Redis缓存
		cache.putObject(componentType, componentTypeList);
		return componentTypeList;
	}

}
