package com.pkpmcloud.dao;

import com.pkpmcloud.model.Project;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/5/9
 */
public interface ProjectDAO {

    List<Project> listValidProject();
}
