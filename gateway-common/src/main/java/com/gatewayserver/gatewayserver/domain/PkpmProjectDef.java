package com.gatewayserver.gatewayserver.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PkpmProjectDef implements Serializable {
    private String projectId;
    private String areaName;
    private String areaCode;
    private String workspaceId;
    private String adIpAddress;
}