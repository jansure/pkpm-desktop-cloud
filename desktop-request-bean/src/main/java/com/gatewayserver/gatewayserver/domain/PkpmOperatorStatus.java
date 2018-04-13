package com.gatewayserver.gatewayserver.domain;

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
    //默认生成随机jobId;
    private String jobId = StringUtil.getUUID();
    private String projectId;
    private Integer userId;
    private Integer subsId;
    private Integer adId;
    private String userName;
    private String desktopId = "";
    private String computerName = "";
    private String operatorType;
    private String status = JobStatusEnum.INITIAL.toString();
    //设置默认初始值
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime = LocalDateTime.now();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime = LocalDateTime.now();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime finishTime = LocalDateTime.now();
    private Integer isFinished = 0;
}
