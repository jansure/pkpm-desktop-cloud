package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.pkpmdesktopcloud.desktopcloudbusiness.constants.SysConstant;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
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
@Slf4j
public class PkpmCloudComponentDefServiceImpl implements PkpmCloudComponentDefService {
	
	private static final String PRODUCT_INFO_LIST_ID = "productInfoList";
	
	@Resource
	private PkpmCloudComponentDefDAO componentDAO;
	
	@Resource
	private PkpmCloudProductDefDAO productDAO;

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
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getList()  
	 */  
	    
	@Override
	public List<PkpmCloudComponentDef> getList() {
		
		return componentDAO.getList();
	}


	/* (non-Javadoc)
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getSoftwareTypeList()
	 */
	@Override
	public List<PkpmCloudComponentDef> getSoftwareTypeList() {

		return componentDAO.getSoftwareTypeList();
	}


	/* (non-Javadoc)
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getComponentDefListByProductType(java.lang.Integer)
	 */
	@Override
	public Map<Integer, List<PkpmCloudComponentDef>> getComponentDefListByProductType(Integer productType) {
		RedisCache cache = new RedisCache(PRODUCT_INFO_LIST_ID);
		Map<Integer, List<PkpmCloudComponentDef>> componentsMap = (Map<Integer, List<PkpmCloudComponentDef>>) cache.getObject(productType);
		
		// 若存在Redis缓存，从缓存中读取
		if (componentsMap != null) {
			return componentsMap;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		List<PkpmCloudComponentDef> componentDefList = new ArrayList<PkpmCloudComponentDef>();
		// 根据productType查询对应的product，得到component_id列表
		List<PkpmCloudProductDef> productList = productDAO.getProductByType(productType);
		// 循环component_id，找到对应的component_def
		if(productList != null && productList.size() > 0) {
			for (PkpmCloudProductDef product : productList) {
				PkpmCloudComponentDef component = componentDAO.getComponentInfoById(product.getComponentId());
				componentDefList.add(component);
			}
		}
		// 按照ComponentType进行分组
		componentsMap = componentDefList.stream().collect(Collectors.groupingBy(PkpmCloudComponentDef::getComponentType));
		
		// 存入redis
		cache.putObject(productType, componentsMap);
		return componentsMap;
	}

}
