package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.gateway.common.domain.PkpmAdDef;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmAdDefMapper {
    @Select("select * from pkpm_ad_def (#{pkpmAdDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmAdDef> select(PkpmAdDef pkpmAdDef);

    @Insert("insert into pkpm_ad_def (#{pkpmAdDef})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "adId", keyColumn = "ad_id")
    Integer insert(PkpmAdDef pkpmAdDef);
}
