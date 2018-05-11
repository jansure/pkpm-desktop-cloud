package com.cabr.pkpm.service;

import java.util.List;

import com.cabr.pkpm.entity.Subscription.Subscription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.utils.ResponseResult;
import com.gateway.common.domain.PkpmOperatorStatus;


public interface ISubscriptionService {

	PkpmOperatorStatus saveSubsDetails(UserInfo userInfo,WorkOrderVO wo);

	List<Subscription> findSubscriptionByUserId(Integer userId);
	/**
	 *根据subsId更新订单状态
	 *
	 * @author xuhe
	 * @param Subscription
	 * @return java.lang.String
	 */
	String updateSubscriptionBySubsId(Subscription Subscription);

	List<Subscription> findSubscriptionByProductName(Integer userId);

	String getAvailableComputerName(Integer productId, String projectId,Integer adId);
}
