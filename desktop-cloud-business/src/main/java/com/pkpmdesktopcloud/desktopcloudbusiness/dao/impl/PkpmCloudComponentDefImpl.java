package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmCloudComponentDefMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

@Repository
public class PkpmCloudComponentDefImpl implements PkpmCloudComponentDefDAO{
	
	@Autowired
	private PkpmCloudComponentDefMapper componentMapper;
	
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
	@Override
    public String getComponentName(Integer componentId, Integer componentType) {
		
		PkpmCloudComponentDef componentInfo = getComponentInfo(componentId, componentType);
		
		if(componentInfo != null) {
			return componentInfo.getComponentName();
		}
		
		return "";
    }
    
	@Override
	public PkpmCloudComponentDef getComponentInfo(Integer componentId, Integer componentType) {
		
		PkpmCloudComponentDef componentInfo = new PkpmCloudComponentDef();
		componentInfo.setComponentId(componentId);
		componentInfo.setComponentType(componentType);
		
		List<PkpmCloudComponentDef> list = componentMapper.select(componentInfo );
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
		
		
		List<PkpmCloudComponentDef> list = componentMapper.getComponentTypeList();
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
		PkpmCloudComponentDef componentInfo = new PkpmCloudComponentDef();
		componentInfo.setComponentType(componentType);
		
		List<PkpmCloudComponentDef> list = componentMapper.select(componentInfo );
		if(list != null && list.size() > 0) {
			
			//obj2Map
			
			
		}
		return null;
	}
	
	@Override
	public List<ComponentVO> getComponentListByProductType(Integer productType){
		return componentMapper.getComponentListByProductType(productType);
	}

	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO#getList()  
	 */  
	    
	@Override
	public List<PkpmCloudComponentDef> getList() {
		
		PkpmCloudComponentDef componentInfo = new PkpmCloudComponentDef();
		
		return componentMapper.select(componentInfo );
	}
}
