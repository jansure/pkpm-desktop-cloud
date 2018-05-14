package com.pkpmcloud.dao.mapper;

import com.desktop.utils.mybatis.SimpleSelectLangDriver;
import com.pkpmcloud.model.Project;
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
public interface ProjectMapper {

    @Select("SELECT * FROM pkpm_shutdown_project (#{project})")
    @Lang(SimpleSelectLangDriver.class)
    List<Project> select(Project project);
}
