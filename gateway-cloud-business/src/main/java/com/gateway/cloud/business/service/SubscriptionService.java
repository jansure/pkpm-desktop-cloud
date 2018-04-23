package com.gateway.cloud.business.service;

import java.util.List;

import com.gateway.cloud.business.entity.SubsCription;
import com.gateway.cloud.business.entity.UserInfo;
import com.gateway.cloud.business.utils.ResponseResult;
import com.gateway.cloud.business.vo.WorkOrderVO;
import com.gateway.common.domain.PkpmOperatorStatus;


public interface SubscriptionService {

	PkpmOperatorStatus saveSubsDetails(UserInfo userInfo,WorkOrderVO wo);

	List<SubsCription> findSubsCriptionByUserId(Integer userId);
	/**
	 *根据subsId更新订单状态
	 *
	 * @author xuhe
	 * @param subsCription
	 * @return java.lang.String
	 */
	String updateSubsCriptionBySubsId(SubsCription subsCription);
}
