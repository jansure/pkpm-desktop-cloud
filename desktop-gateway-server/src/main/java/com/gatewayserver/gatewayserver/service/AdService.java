package com.gatewayserver.gatewayserver.service;


import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.AdUser;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdService {




    void addAdUser(CommonRequestBean requestBean) ;

    void updateAdUser(CommonRequestBean requestBean);

    List<AdUser> getUsersByAdId(Integer adId);
    int getUserCountByAdIpAddress(String adIpAddress);

    ResultObject getComputersByAdId(Integer adId);

    /**
     * @Description: 检查用户是否存在
     * @Param: * @param userName,adId
     * @Return boolean
     */
    boolean checkUser(String userName, Integer adId);

    ResultObject deleteUser(String userName, Integer adId);

    ResultObject deleteComputer(String computerName, Integer adId);

}