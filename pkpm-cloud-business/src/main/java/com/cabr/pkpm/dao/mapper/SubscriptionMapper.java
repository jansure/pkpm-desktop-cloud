package com.cabr.pkpm.dao.mapper;

import com.cabr.pkpm.entity.subscription.SubsCription;
import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.gatewayserver.gatewayserver.domain.PkpmCloudUserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/4/17
 */
public interface SubscriptionMapper {

    @Insert("insert into pkpm_cloud_subscription (#{subsCription})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyColumn = "id")
    Integer insert(SubsCription subsCription);

    @Select("select * from pkpm_cloud_subscription (#{subsCription})")
    @Lang(SimpleSelectLangDriver.class)
    List<SubsCription> select(SubsCription subsCription);

    @Update("update pkpm_cloud_subscription (#{subsCription}) WHERE subs_id = #{subsId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(SubsCription subsCription);
}
