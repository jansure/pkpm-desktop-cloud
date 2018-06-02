package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;
import java.util.Map;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.ComponentVO;

public interface PkpmCloudComponentDefDAO {
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
    String getComponentName(Integer componentId, Integer componentType);
    
    PkpmCloudComponentDef getComponentInfo(Integer componentId, Integer componentType);
    
    List<ComponentVO> getComponentListByProductType(Integer productType);

	/**  
	 * @Title: getList  
	 * @Description: TODO(这里用一句话描述这个方法的作用)  
	 * @param @return    参数  
	 * @return List<ComponentVO>    返回类型  
	 * @throws  
	 */  
	List<PkpmCloudComponentDef> getList();
	
	/**  
	 * @Title: getSoftwareTypeList  
	 * @Description: 软件分类  
	 * @param @return    参数  
	 * @return List<PkpmCloudComponentDef>    返回类型  
	 * @throws  
	 */  
	List<PkpmCloudComponentDef> getSoftwareTypeList();
	
	/**
	 * 根据componentId获取PkpmCloudComponentDef
	 * @param componentId
	 * @return
	 */
	PkpmCloudComponentDef getComponentInfoById(Integer componentId);
}
