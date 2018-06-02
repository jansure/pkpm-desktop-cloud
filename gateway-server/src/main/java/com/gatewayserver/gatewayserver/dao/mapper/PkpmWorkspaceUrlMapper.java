package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.gateway.common.domain.PkpmWorkspaceUrl;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PkpmWorkspaceUrlMapper {
    @Select("select * from pkpm_workspace_url (#{pkpmWorkspaceUrl})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmWorkspaceUrl> select(PkpmWorkspaceUrl pkpmWorkspaceUrl);

}
