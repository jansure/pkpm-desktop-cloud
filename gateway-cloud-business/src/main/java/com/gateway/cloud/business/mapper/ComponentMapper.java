package com.gateway.cloud.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gateway.cloud.business.entity.ComponentInfo;

@Mapper
public interface ComponentMapper {
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
    String getComponentName(@Param("componentId") Integer componentId, @Param("componentType") Integer componentType);
    
    ComponentInfo getComponentInfo(@Param("componentId") Integer componentId,@Param("componentType")  Integer componentType);
}
