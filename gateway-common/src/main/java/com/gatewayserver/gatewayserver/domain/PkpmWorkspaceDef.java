package com.gatewayserver.gatewayserver.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PkpmWorkspaceDef implements Serializable {
    private String workspaceId;
    private String workspaceName;
    private String adminName;
    private String adminPassword;
}
