package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatusHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmOperatorStatusHistoryMapper {
    @Select("select * from pkpm_operator_status_history (#{operatorStatusHistory})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmOperatorStatusHistory> select(PkpmOperatorStatusHistory operatorStatusHistory);

    @Update("update pkpm_operator_status_history (#{operatorStatusHistory}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmOperatorStatusHistory operatorStatusHistory);

    @Insert("insert into pkpm_operator_status_history (#{operatorStatusHistory})")
    @Lang(SimpleInsertLangDriver.class)
    Integer save(PkpmOperatorStatusHistory operatorStatusHistory);

    Integer saveOperatorStatus(PkpmOperatorStatusHistory operatorStatusHistory);

    @Select("select * from pkpm_operator_status_history (#{operatorStatusHistory})")
    @Lang(SimpleSelectLangDriver.class)
    PkpmOperatorStatusHistory selectOperatorStatus(PkpmOperatorStatusHistory operatorStatusHistory);

}
