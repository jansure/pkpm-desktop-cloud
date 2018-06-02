package com.gateway.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmWorkspaceUrl implements Serializable {
    private String serviceName;
    private String url;
    private String areaName;
    private String projectId;
    private String jobId;
    private String restfulAccessType;
    private String desktopId;
}
