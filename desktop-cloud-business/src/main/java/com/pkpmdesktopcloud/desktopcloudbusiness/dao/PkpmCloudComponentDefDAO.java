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
    
    /**
     * 返回购买配置项类型列表(如地域、软件名称、主机配置、云存储)
     * @return
     */
    List<Map<String, Object>> getComponentTypeList();
    
    /**
     * 根据配置项类型返回对应的所有配置项
     * @param componentType
     * @return
     */
    List<Map<String, Object>> getConfigByComponentType(Integer componentType);
    
    List<ComponentVO> getComponentListByProductType(Integer productType);
}
