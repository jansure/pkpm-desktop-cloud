package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gateway.common.domain.PkpmOperatorStatus;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmOperatorStatusMapper {
    @Select("select * from pkpm_operator_status (#{pkpmOperatorStatus})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmOperatorStatus> select(PkpmOperatorStatus pkpmOperatorStatus);

    @Update("update pkpm_operator_status (#{pkpmOperatorStatus}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmOperatorStatus pkpmOperatorStatus);

    @Insert("insert into pkpm_operator_status (#{pkpmOperatorStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Lang(SimpleInsertLangDriver.class)
    Integer save(PkpmOperatorStatus pkpmOperatorStatus);

    Integer saveOperatorStatus(PkpmOperatorStatus pkpmOperatorStatus);

    @Select("select * from pkpm_operator_status (#{pkpmOperatorStatus})")
    @Lang(SimpleSelectLangDriver.class)
    PkpmOperatorStatus selectOperatorStatus(PkpmOperatorStatus pkpmOperatorStatus);

    @Select("SELECT *  FROM  pkpm_operator_status" +
            "WHERE  desktop_id IN (#{desktopIds,jdbcType=VARCHAR})"  +
            "AND STATUS = #{operatoreType,jdbcType=VARCHAR}"    +
            "and operator_type = #{jobStatus,jdbcType=VARCHAR} ")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmOperatorStatus> queryPkpmOperatorStatusByDesktopIds(@Param("desktopIds") String desktopIds,@Param("operatoreType") String operatoreType,@Param("jobStatus") String jobStatus);

}
