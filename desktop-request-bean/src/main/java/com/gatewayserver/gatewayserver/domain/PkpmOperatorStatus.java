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
    //默认值为null,强制赋值；
    private String projectId =null;
    private Integer userId =-1;//默认值为-1，
    private Long subsId =-1L;
    private Integer adId=null;
    private String userName=null;
    private String desktopId = "";
    private String computerName = "";
    private String operatorType=null;
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
