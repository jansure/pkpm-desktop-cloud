package com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmSysConfig;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PkpmSysConfigMapper {
	
	@Select("select * from pkpm_sys_config (#{sysConfig})")
	@Lang(SimpleSelectLangDriver.class)
	List<PkpmSysConfig> select(PkpmSysConfig sysConfig);

	  
	/**  
	 * @Title: getPkpmSysConfigByKey  
	 * @Description: 通过Key获取配置信息  
	 * @param key 
	 * @return PkpmSysConfig   配置信息  
	 * @throws  
	 */  
	@Select("SELECT `key`, value FROM pkpm_sys_config WHERE `key`= #{key}")
	PkpmSysConfig getPkpmSysConfigByKey(@Param("key")String key);
}
