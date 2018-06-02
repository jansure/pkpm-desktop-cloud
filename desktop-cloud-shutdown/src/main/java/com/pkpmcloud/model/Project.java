package com.pkpmcloud.model;

import lombok.Data;

import java.util.Date;

/**
 * @author xuhe
 * @description
 * @date 2018/5/9
 */
@Data
public class Project {

    private Integer id;
    private String projectId;
    private String projectDesc;
    private String areaName;
    private String adminName;
    private String adminPassword;
    private Integer valid;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", adminName='" + adminName + '\'' +
                '}';
    }
}
