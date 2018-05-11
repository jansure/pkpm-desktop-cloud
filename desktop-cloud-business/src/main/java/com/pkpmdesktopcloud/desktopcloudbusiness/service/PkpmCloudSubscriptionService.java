package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;

import com.gateway.common.domain.PkpmOperatorStatus;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.WorkOrderVO;


public interface PkpmCloudSubscriptionService {

	PkpmOperatorStatus saveSubsDetails(PkpmCloudUserInfo userInfo,WorkOrderVO wo);

	List<PkpmCloudSubscription> findSubscriptionByUserId(Integer userId);
	
	/**
	 *根据subsId更新订单状态
	 *
	 * @author xuhe
	 * @param Subscription
	 * @return java.lang.String
	 */
	String updateSubscriptionBySubsId(PkpmCloudSubscription Subscription);
}
