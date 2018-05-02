package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.desktop.utils.mybatis.SimpleInsertLangDriver;
import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.desktop.utils.mybatis.SimpleUpdateLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
@Mapper
public interface PkpmCloudSubscriptionMapper {
	
	@Select("select * from pkpm_cloud_subscription (#{subsCription})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmCloudSubscription> getSubsCriptionList(PkpmCloudSubscription subsCription );
    
//    @Insert("insert into pkpm_cloud_subscription (#{subsCription})")
//    @Lang(SimpleInsertLangDriver.class)
//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(PkpmCloudSubscription subsCription);

    @Update("update pkpm_cloud_subscription (#{subsCription}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmCloudSubscription subsCription);

}
