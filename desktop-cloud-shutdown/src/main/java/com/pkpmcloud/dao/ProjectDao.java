package com.pkpmcloud.dao;

import com.pkpmcloud.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/5/9
 */
public interface ProjectDao {

    List<Project> listValidProject();

    Project getProject(String projectId);
}
