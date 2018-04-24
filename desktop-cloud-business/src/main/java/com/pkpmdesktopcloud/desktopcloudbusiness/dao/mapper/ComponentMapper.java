package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;

@Mapper
public interface ComponentMapper {
	/**
     * 根据componentId和componentType获取对应的子产品名
     * @param componentId,componentId
     * @return
     */
    String getComponentName(@Param("componentId") Integer componentId, @Param("componentType") Integer componentType);
    
    @Select("select * from pkpm_ad_def (#{pkpmAdDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<ComponentInfo> getComponentInfo(@Param("componentId") Integer componentId,@Param("componentType")  Integer componentType);
}
