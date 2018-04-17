package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmProjectDef;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PkpmProjectDefMapper {
    @Select("select * from pkpm_project_def (#{pkpmProjectDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmProjectDef> select(PkpmProjectDef pkpmProjectDef);

}
