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
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudNavigation;

@Mapper
public interface PkpmCloudNavigationMapper {

    @Select("select * from pkpm_cloud_navigation (#{pkpmCloudNavigation})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudNavigation> select(PkpmCloudNavigation pkpmCloudNavigation );
    
    @Insert("insert into pkpm_cloud_navigation (#{pkpmCloudNavigation})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "navId", keyColumn = "nav_id")
    Integer insert(PkpmCloudNavigation pkpmCloudNavigation);

    @Update("update pkpm_cloud_navigation (#{pkpmCloudNavigation}) WHERE nav_id = #{navId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmCloudNavigation pkpmCloudNavigation);
    

}
