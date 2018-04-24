package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ComponentDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.ComponentMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.ProductService;

@Repository
public class ComponentDAOImpl implements ComponentDAO{
	
	@Autowired
	private ComponentMapper componentMapper;
	
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
	@Override
    public String getComponentName(Integer componentId, Integer componentType) {
		
		ComponentInfo componentInfo = getComponentInfo(componentId, componentType);
		
		if(componentInfo != null) {
			return componentInfo.getComponentName();
		}
		
		return "";
    }
    
	@Override
	public ComponentInfo getComponentInfo(Integer componentId, Integer componentType) {
		
		ComponentInfo componentInfo = new ComponentInfo();
		componentInfo.setComponentId(componentId);
		componentInfo.setComponentType(componentType);
		
		List<ComponentInfo> list = componentMapper.getComponentInfo(componentInfo );
		if(list != null && list.size() > 0) {
			
				return list.get(0);
		}
    	
		return null;
	}
	
}
