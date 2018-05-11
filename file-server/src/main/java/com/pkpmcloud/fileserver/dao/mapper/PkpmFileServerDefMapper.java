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
import com.pkpmcloud.fileserver.domain.PkpmFileServerDef;

@Mapper
public interface PkpmFileServerDefMapper {

    @Select("select * from pkpm_file_server_def (#{fileServerDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmFileServerDef> select(PkpmFileServerDef fileServerDef );
    
    @Insert("insert into pkpm_file_server_def (#{fileServerDef})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(PkpmFileServerDef fileServerDef);

    @Update("update pkpm_file_server_def (#{fileServerDef}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmFileServerDef fileServerDef);

}