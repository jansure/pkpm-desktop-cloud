package com.pkpmcloud.dao.mapper;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmcloud.model.ComputerDef;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@Mapper
public interface WhitelistMapper {

    @Select("SELECT * FROM pkpm_shutdown_whitelist (#{computerDef})")
    @Lang(SimpleSelectLangDriver.class)
    Set<ComputerDef> listComputer(ComputerDef computerDef);
}
