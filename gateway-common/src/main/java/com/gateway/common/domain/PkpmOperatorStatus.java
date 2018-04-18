package com.gateway.common.domain;

import com.desktop.constant.JobStatusEnum;
import com.desktop.utils.StringUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PkpmOperatorStatus implements Serializable {

    private Integer id;
    private String jobId;
    private String projectId;
    private Integer userId;
    private Long subsId;
    private Integer adId;
    private String userName;
    private String desktopId;
    private String computerName;
    private String operatorType;
    private String areaCode;
    private String status;

    public PkpmOperatorStatus() {
    }
    /**
     *@author xuhe
     */
    //为实例设置默认值
    //projectId,areaCode,operateType,adId,subsId必须在CommonRequestBean里赋值
    public PkpmOperatorStatus setDefault() {
        //默认生成随机字符串
        this.jobId = StringUtil.getUUID();
        this.userName = "";
        this.desktopId = "";
        this.computerName = "";
        this.status = JobStatusEnum.INITIAL.toString();
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.finishTime = LocalDateTime.now();
        this.isFinished = 0;
        return this;
    }

    //设置默认初始值
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime finishTime;
    private Integer isFinished;
}
