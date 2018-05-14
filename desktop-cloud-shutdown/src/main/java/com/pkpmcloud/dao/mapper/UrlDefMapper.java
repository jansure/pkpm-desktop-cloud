package com.pkpmcloud.dao.mapper;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmcloud.model.UrlDef;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@Mapper
public interface UrlDefMapper {

    @Select("SELECT * FROM pkpm_workspace_url (#{urlDef})")
    @Lang(SimpleSelectLangDriver.class)
    List<UrlDef> listUrl(UrlDef urlDef);
}
