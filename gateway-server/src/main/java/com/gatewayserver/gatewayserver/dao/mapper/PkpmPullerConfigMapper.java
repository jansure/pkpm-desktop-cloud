package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmPullerConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmPullerConfigMapper {
    @Insert("insert into pkpm_puller_config (#{pullConfig})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(PkpmPullerConfig pullConfig);

    @Select("select * from pkpm_puller_config (#{pullConfig})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmPullerConfig> select(PkpmPullerConfig pullConfig);

    @Update("update pkpm_puller_config (#{pullConfig}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmPullerConfig pullConfig);
}
