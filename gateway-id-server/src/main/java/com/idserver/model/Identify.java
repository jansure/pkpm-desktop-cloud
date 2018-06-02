package com.idserver.model;

import lombok.Data;

import java.util.Date;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@Data
public class Identify {

    private Integer id;

    /**根据adId进行筛选*/
    private Integer adId;
    /**id前缀，如产品名*/
    private String identifyPrefix;
    /**id数字后缀 同一adid 同一产品id 自增*/
    private Integer identifyIndex;

    private Date createTime;
    private Date updateTime;
}
