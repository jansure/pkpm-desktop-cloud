package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ComponentInfo;

@Mapper
public interface ComponentMapper {

    @Select("select * from pkpm_cloud_component_def (#{componentInfo})")
    @Lang(SimpleSelectLangDriver.class)
    List<ComponentInfo> getComponentInfo(ComponentInfo componentInfo );
    
    @Insert("insert into pkpm_cloud_component_def (#{componentInfo})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "componentId", keyColumn = "component_id")
    Integer insert(ComponentInfo componentInfo);

    @Update("update pkpm_cloud_component_def (#{componentInfo}) WHERE component_id = #{componentId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(ComponentInfo componentInfo);
    
    
}
