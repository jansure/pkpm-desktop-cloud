package com.cabr.pkpm.service;

import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.StatusObject;

public interface IStatusService {

	StatusObject queryDesktopStatus(CommonRequestBean commonRequestBean);

	String queryOperateStatus(CommonRequestBean commonRequestBean);

}
