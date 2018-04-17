package com.gatewayserver.gatewayserver.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PkpmOperatorStatusHistory implements Serializable {
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
    private String status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime finishTime;
    private Integer isFinished;
}
