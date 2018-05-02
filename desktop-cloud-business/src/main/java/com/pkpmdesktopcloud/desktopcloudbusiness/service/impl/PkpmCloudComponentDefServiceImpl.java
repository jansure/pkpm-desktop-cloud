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
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudComponentDefService#getList()  
	 */  
	    
	@Override
	public List<PkpmCloudComponentDef> getList() {
		
		return componentDAO.getList();
	}
}
