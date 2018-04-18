package com.cabr.pkpm.service.status;

import com.gateway.common.domain.CommonRequestBean;

public interface IStatusService {

	String queryDesktopStatus(CommonRequestBean commonRequestBean);

	String queryOperateStatus(CommonRequestBean commonRequestBean);

}
