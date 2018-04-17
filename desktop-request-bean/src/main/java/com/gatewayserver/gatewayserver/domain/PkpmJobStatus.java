package com.gatewayserver.gatewayserver.domain;

import com.desktop.utils.mybatis.Invisible;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmJobStatus implements Serializable {
    private String areaCode;
    private String workspaceId;
    private String projectId;
    private String jobId;
    private LocalDateTime createTime;
    private String status;
    private String ext1;
    private String operatorType;
    @Invisible
    private Integer page;
    @Invisible
    private Integer pageSize;
}
