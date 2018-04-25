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
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;

@Mapper
public interface UserMapper {
	
	@Select("select * from pkpm_cloud_user_info (#{userInfo})")
    @Lang(SimpleSelectLangDriver.class)
	List<UserInfo> findUserInfoList(UserInfo userInfo);
	
	@Insert("insert into pkpm_cloud_user_info (#{userInfo})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    Integer insert(UserInfo userInfo);

    @Update("update pkpm_cloud_user_info (#{userInfo}) WHERE user_id = #{userId}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(UserInfo userInfo);

}
