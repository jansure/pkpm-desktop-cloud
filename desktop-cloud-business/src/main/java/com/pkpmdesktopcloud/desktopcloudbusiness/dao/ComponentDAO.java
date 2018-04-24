package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;

public interface ComponentDAO {
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
    String getComponentName(Integer componentId, Integer componentType);
    
    ComponentInfo getComponentInfo(Integer componentId, Integer componentType);
    
}
