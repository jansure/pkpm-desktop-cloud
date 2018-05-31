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


    @Select(("<script> " +
            "SELECT * FROM pkpm_operator_status AS operator" +
            " <where> operator.is_finished=1"+
            //企业ID
            " <if test =\"computerName != null \">" +
            " and computer_name like concat ('%',#{computerName},'%')" +
            " </if> test=\"createTime != null\"> " +
            " and date_format(create_time,'%Y-%m-%d %H:%i:%S') " +
            " <![CDATA[ >= ]]> date_format(#{createTime},'%Y-%m-%d %H:%i:%S') " +
            " </if> test=\"updateTime != null\"> " +
            " and date_format(update_time,'%Y-%m-%d %H:%i:%S') " +
            " <![CDATA[ >= ]]> date_format(#{updateTime},'%Y-%m-%d %H:%i:%S') " +
            " </if> " )

    )
    List<PkpmOperatorStatus> selectComputerList(PkpmOperatorStatus pkpmOperatorStatus);

}
