package com.gatewayserver.gatewayserver.dao;


import com.gatewayserver.gatewayserver.domain.PkpmWorkspaceUrl;

public interface PkpmWorkspaceUrlDAO {
    /**
     * 根据projectId，areaName，serviceName查询接口Url
     *
     * @param projectId
     * @param areaName
     * @param serviceName
     * @return
     */
    PkpmWorkspaceUrl selectByPriKey(String projectId, String areaName, String serviceName);

    /**
     * 根据projectId，areaName，serviceName查询接口Url  + 请求类型
     *
     * @param projectId
     * @param areaName
     * @param serviceName
     * @param type
     * @return
     */
    PkpmWorkspaceUrl selectByPriKey(String projectId, String areaName, String serviceName, String type);

    PkpmWorkspaceUrl select(PkpmWorkspaceUrl pkpmWorkspaceUrl);

}
