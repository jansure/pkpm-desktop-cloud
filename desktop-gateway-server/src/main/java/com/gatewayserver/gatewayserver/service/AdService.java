package com.gatewayserver.gatewayserver.service;


import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.ad.AdComputer;
import com.gatewayserver.gatewayserver.dto.ad.AdUser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdService {


    String addAdUser(CommonRequestBean requestBean) ;

    String updateAdUser(CommonRequestBean requestBean);

    int getUserCountByAdIpAddress(String adIpAddress);

    int getUserOuCountByAdId(Integer adId);

    List<AdUser> getUsersByAdId(Integer adId);

    List<AdComputer>  getComputersByAdId(Integer adId);

    /**
     * @Description: 检查用户是否存在
     * @Param: * @param userName,adId
     * @Return boolean
     */
    boolean checkUser(String userName, Integer adId);

    void deleteUser(String userName, Integer adId);

    void deleteComputer(String computerName, Integer adId);

}