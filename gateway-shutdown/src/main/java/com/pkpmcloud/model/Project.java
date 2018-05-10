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
    private String areaName;
    private String areaDesc;
    private String adminName;
    private String adminPassword;
    private Boolean valid;
    private Date createTime;
    private Date updateTime;


}
