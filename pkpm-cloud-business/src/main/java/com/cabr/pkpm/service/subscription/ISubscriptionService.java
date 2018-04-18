package com.cabr.pkpm.service.subscription;

import java.util.List;

import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.utils.ResponseResult;
import com.gatewayserver.gatewayserver.domain.PkpmOperatorStatus;


public interface ISubscriptionService {

	PkpmOperatorStatus saveSubsDetails(UserInfo userInfo,WorkOrderVO wo);

	List<SubsCription> findSubsCriptionByUserId(Integer userId);
}
