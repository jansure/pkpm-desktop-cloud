package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;

@Mapper
public interface ComponentDAO {
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
    String getComponentName(@Param("componentId") Integer componentId, @Param("componentType") Integer componentType);
    
    ComponentInfo getComponentInfo(@Param("componentId") Integer componentId,@Param("componentType")  Integer componentType);
}
