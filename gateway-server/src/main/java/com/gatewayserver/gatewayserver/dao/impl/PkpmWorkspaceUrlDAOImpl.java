package com.gatewayserver.gatewayserver.dao.impl;

import com.gateway.common.domain.PkpmWorkspaceUrl;
import com.gatewayserver.gatewayserver.dao.PkpmWorkspaceUrlDAO;
import com.gatewayserver.gatewayserver.dao.mapper.PkpmWorkspaceUrlMapper;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PkpmWorkspaceUrlDAOImpl implements PkpmWorkspaceUrlDAO {
    @Resource
    private PkpmWorkspaceUrlMapper mapper;

    @Override
    public PkpmWorkspaceUrl selectByPriKey(String projectId, String areaName, String serviceName) {
        PkpmWorkspaceUrl criteria = new PkpmWorkspaceUrl();
        criteria.setProjectId(projectId);
        criteria.setAreaName(areaName);
        criteria.setServiceName(serviceName);
        List<PkpmWorkspaceUrl> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

    @Override
    public PkpmWorkspaceUrl selectByPriKey(String projectId, String areaName, String serviceName, String type) {
        PkpmWorkspaceUrl criteria = new PkpmWorkspaceUrl();
        criteria.setProjectId(projectId);
        criteria.setAreaName(areaName);
        criteria.setServiceName(serviceName);
        criteria.setRestfulAccessType(type);
        List<PkpmWorkspaceUrl> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }

    @Override
    public PkpmWorkspaceUrl select(PkpmWorkspaceUrl pkpmWorkspaceUrl) {
        //fixme
        PkpmWorkspaceUrl criteria = new PkpmWorkspaceUrl();
        criteria.setProjectId(pkpmWorkspaceUrl.getProjectId());
        criteria.setAreaName(pkpmWorkspaceUrl.getAreaName());
        criteria.setServiceName(pkpmWorkspaceUrl.getServiceName());
        criteria.setRestfulAccessType(pkpmWorkspaceUrl.getRestfulAccessType());
        List<PkpmWorkspaceUrl> list = mapper.select(criteria);
        if (CollectionUtils.isNotEmpty(list))
            return list.get(0);
        return null;
    }
}
