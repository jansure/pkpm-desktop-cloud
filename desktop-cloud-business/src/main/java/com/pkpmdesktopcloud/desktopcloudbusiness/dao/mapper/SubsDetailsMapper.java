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
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails;

@Mapper
public interface SubsDetailsMapper {
	
	@Select("select * from pkpm_cloud_subs_details (#{subsDetails})")
    @Lang(SimpleSelectLangDriver.class)
	List<SubsDetails> findSubsDetailsList(SubsDetails subsDetails);
	
	@Insert("insert into pkpm_cloud_subs_details (#{subsDetails})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(SubsDetails subsDetails);

    @Update("update pkpm_cloud_subs_details (#{subsDetails}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(SubsDetails subsDetails);

}
