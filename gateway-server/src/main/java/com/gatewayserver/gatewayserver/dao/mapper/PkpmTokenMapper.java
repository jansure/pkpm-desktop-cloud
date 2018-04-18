package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gateway.common.domain.PkpmToken;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmTokenMapper {
    @Insert("insert into pkpm_token (#{pkpmToken})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "projectId", keyColumn = "project_id")
    Integer insert(PkpmToken pkpmToken);

    @Select("select * from pkpm_token (#{pkpmToken})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmToken> select(PkpmToken pkpmToken);

    @Update("update pkpm_token (#{pkpmToken}) WHERE project_id = #{projectId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmToken pkpmToken);
}
