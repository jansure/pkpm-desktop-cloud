package com.gatewayserver.gatewayserver.dao;


import com.gatewayserver.gatewayserver.domain.PkpmWorkspaceDef;

public interface PkpmWorkspaceDefDAO {
    PkpmWorkspaceDef selectById(String workspaceId);
}
