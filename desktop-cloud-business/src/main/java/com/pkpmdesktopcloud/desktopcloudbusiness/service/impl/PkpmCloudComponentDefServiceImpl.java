package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService;
import com.pkpmdesktopcloud.redis.RedisCache;

/**
 * 产品接口实现类
 * 
 * @author yangpengfei
 *
 */
@Service
public class PkpmCloudComponentDefServiceImpl implements PkpmCloudComponentDefService {
	
	private static final String COMPOENT_TYPE_LIST_ID = "componentTypeList";
	
	private static final String ALL_TYPE_REDIS_KEY = "allTypeRedisKey";
	
	@Resource
	private PkpmCloudComponentDefDAO componentDAO;

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param productType
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getComponentListByProductType(java.lang.Integer)  
	 */  
	@Override
	public List<ComponentVO> getComponentListByProductType(Integer productType) {
		return componentDAO.getComponentListByProductType(productType);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getComponentTypeList()  
	 */  
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


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getList()  
	 */  
	    
	@Override
	public List<PkpmCloudComponentDef> getList() {
		
		return componentDAO.getList();
	}
}
