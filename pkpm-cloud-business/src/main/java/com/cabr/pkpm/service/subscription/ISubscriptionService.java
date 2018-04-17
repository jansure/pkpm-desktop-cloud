package com.cabr.pkpm.service.subscription;

import java.util.List;

import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.utils.ResponseResult;


public interface ISubscriptionService {

	ResponseResult saveSubsDetails(UserInfo userInfo,WorkOrderVO wo);

	List<SubsCription> findSubsCriptionByUserId(Integer userId);
}
