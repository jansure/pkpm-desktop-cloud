package com.gatewayserver.gatewayserver.service;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;

public interface StatusService {


    String queryDesktopStatus(CommonRequestBean commonRequestBean);

    ResultObject queryOperateStatus(CommonRequestBean commonRequestBean);

}
