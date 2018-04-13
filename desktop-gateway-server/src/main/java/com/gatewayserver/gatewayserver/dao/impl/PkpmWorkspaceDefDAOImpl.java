package com.gatewayserver.gatewayserver.dao.impl;

import com.gatewayserver.gatewayserver.dao.PkpmWorkspaceDefDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmWorkspaceDefMapper;
import com.gatewayserver.gatewayserver.domain.PkpmWorkspaceDef;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmWorkspaceDefDAOImpl implements PkpmWorkspaceDefDAO {
    @Resource
    private PkpmWorkspaceDefMapper mapper;

    @Override
    public PkpmWorkspaceDef selectById(String workspaceId) {
        PkpmWorkspaceDef criteria = new PkpmWorkspaceDef();
        criteria.setWorkspaceId(workspaceId);
        List<PkpmWorkspaceDef> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
}
