package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;

import com.gateway.common.domain.PkpmOperatorStatus;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.WorkOrderVO;


public interface SubscriptionService {

	PkpmOperatorStatus saveSubsDetails(PkpmCloudUserInfo userInfo,WorkOrderVO wo);

	List<PkpmCloudSubscription> findSubsCriptionByUserId(Integer userId);
	
	/**
	 *根据subsId更新订单状态
	 *
	 * @author xuhe
	 * @param subsCription
	 * @return java.lang.String
	 */
	String updateSubsCriptionBySubsId(PkpmCloudSubscription subsCription);
}
