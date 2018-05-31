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
import com.pkpmcloud.fileserver.domain.PkpmFileConfig;

@Mapper
public interface PkpmFileConfigMapper {

    @Select("select * from pkpm_file_config (#{fileConfig})")
    @Lang(SimpleSelectLangDriver.class)
    List<PkpmFileConfig> select(PkpmFileConfig fileConfig );
    
    @Insert("insert into pkpm_file_config (#{fileConfig})")
    @Lang(SimpleInsertLangDriver.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer insert(PkpmFileConfig fileConfig);

    @Update("update pkpm_file_config (#{fileConfig}) WHERE id = #{id}")
    @Lang(SimpleUpdateLangDriver.class)
    Integer update(PkpmFileConfig fileConfig);

}