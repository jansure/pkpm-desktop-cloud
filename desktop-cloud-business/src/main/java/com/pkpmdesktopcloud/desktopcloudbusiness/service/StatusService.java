package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import com.gateway.common.domain.CommonRequestBean;

public interface StatusService {

	String queryDesktopStatus(CommonRequestBean commonRequestBean);

	String queryOperateStatus(CommonRequestBean commonRequestBean);

}
