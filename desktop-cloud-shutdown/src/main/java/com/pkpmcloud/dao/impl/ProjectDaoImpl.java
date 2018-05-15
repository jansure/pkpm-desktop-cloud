package com.pkpmcloud.dao.impl;

import com.pkpmcloud.dao.ProjectDao;
import com.pkpmcloud.dao.mapper.ProjectMapper;
import com.pkpmcloud.model.Project;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/5/14
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {

    @Resource
    ProjectMapper mapper;

    @Override
    public List<Project> listValidProject() {
        Project project = new Project();
        project.setValid(1);
        List<Project> projects = mapper.select(project);
        return projects;
    }

    @Override
    public Project getProject(String projectId) {
        Project project = new Project();
        project.setProjectId(projectId);
        List<Project> projects = mapper.select(project);
        if (projects!=null) {
            return projects.get(0);
        }
        return null;
    }
}
