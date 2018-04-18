package com.gatewayserver.gatewayserver.dao;


import com.gateway.common.domain.PkpmWorkspaceDef;

public interface PkpmWorkspaceDefDAO {
    PkpmWorkspaceDef selectById(String workspaceId);
}
