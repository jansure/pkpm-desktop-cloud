package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ComponentDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.ComponentMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;

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
		
		List<ComponentInfo> list = componentMapper.getComponentInfoList(componentInfo );
		if(list != null && list.size() > 0) {
			
			return list.get(0);
		}
    	
		return null;
	}
	
	 /**
     * 返回购买配置项类型列表(如地域、软件名称、主机配置、云存储)
     * @return
     */
	@Override
	public List<Map<String, Object>> getComponentTypeList() {
		
		
		List<ComponentInfo> list = componentMapper.getComponentTypeList();
		if(list != null && list.size() > 0) {
			
			//obj2Map
			
			
		}
		return null;
	}
	
	 /**
     * 根据配置项类型返回对应的所有配置项
     * @param componentType
     * @return
     */
	@Override
	public List<Map<String, Object>> getConfigByComponentType(Integer componentType){
		ComponentInfo componentInfo = new ComponentInfo();
		componentInfo.setComponentType(componentType);
		
		List<ComponentInfo> list = componentMapper.getComponentInfoList(componentInfo );
		if(list != null && list.size() > 0) {
			
			//obj2Map
			
			
		}
		return null;
	}
	
}
