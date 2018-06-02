package com.gatewayserver.gatewayserver.dao.mapper;


import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gateway.common.domain.PkpmCloudUserInfo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkpmCloudUserInfoMapper {
    @Insert("insert into pkpm_cloud_user_info (#{pkpmCloudUserInfo})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    Integer insert(PkpmCloudUserInfo pkpmCloudUserInfo);

    @Select("select * from pkpm_cloud_user_info (#{pkpmCloudUserInfo})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudUserInfo> select(PkpmCloudUserInfo pkpmCloudUserInfo);

    @Update("update pkpm_cloud_user_info (#{pkpmCloudUserInfo}) WHERE user_id = #{userId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmCloudUserInfo pkpmCloudUserInfo);
}
