package com.gatewayserver.gatewayserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmJobStatus implements Serializable {
    private String workspaceId;
    private String projectId;
    private String jobId;
    private LocalDateTime createTime;
    private String status;
    private String ext1;
    private String operatorType;
}
