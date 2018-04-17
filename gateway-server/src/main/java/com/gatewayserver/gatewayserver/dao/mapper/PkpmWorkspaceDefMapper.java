package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmWorkspaceDef;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PkpmWorkspaceDefMapper {
    @Select("select * from pkpm_workspace_def (#{pkpmWorkspaceDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmWorkspaceDef> select(PkpmWorkspaceDef pkpmWorkspaceDef);

}
