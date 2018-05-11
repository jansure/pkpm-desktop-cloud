package com.pkpmcloud.fileserver.dao.mapper;

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
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

@Mapper
public interface PkpmFileInfoMapper {
	
    @Select("select * from pkpm_file_info (#{fileInfo})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmFileInfo> select(PkpmFileInfo fileInfo );
    
    @Insert("insert into pkpm_file_info (#{fileInfo})")
    @Lang(SimpleInsertLangDriver.class)
    Integer insert(PkpmFileInfo fileInfo);

    @Update("update pkpm_file_info (#{fileInfo}) WHERE md5 = #{md5}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmFileInfo fileInfo);

}