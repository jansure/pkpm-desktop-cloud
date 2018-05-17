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
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudProductDefService;
import com.pkpmdesktopcloud.redis.RedisCache;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class PkpmCloudProductDefServiceImpl implements PkpmCloudProductDefService {
	
	
	private static final String PRODUCT_ID = "product";
	
	private static final String PRODUCT_TYPE_LIST_ID = "productTypeList";
	
	private static final String ALL_TYPE_REDIS_KEY = "allTypeRedisKey";
	
	@Resource
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


	@SuppressWarnings("unchecked")
	@Override
	public List<PkpmCloudProductDef> getProductTypeList() {
		RedisCache cache = new RedisCache(PRODUCT_TYPE_LIST_ID);
		List<PkpmCloudProductDef> productTypeList = (List<PkpmCloudProductDef>)cache.getObject(ALL_TYPE_REDIS_KEY);
		
		// 若存在Redis缓存，从缓存中读取
		if (productTypeList != null) {
			return productTypeList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		productTypeList = productDAO.getProductTypeList();
		for (PkpmCloudProductDef product : productTypeList) {
			// 设置“全家桶类型”为默认套餐
			if (SysConstant.PRODUCT_TYPE_ALL.equals(product.getProductType().toString())) {
				product.setIsDefault(SysConstant.IS_DEFAULT_YES);
			}else {
				product.setIsDefault(SysConstant.IS_DEFAULT_NO);
			}
		}
		
		// 写入Redis缓存
		cache.putObject(ALL_TYPE_REDIS_KEY, productTypeList);
		return productTypeList;
	}
}
