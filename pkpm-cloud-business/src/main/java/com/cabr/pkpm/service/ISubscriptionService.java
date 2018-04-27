package com.cabr.pkpm.service;

import java.util.List;

import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.utils.ResponseResult;
import com.gateway.common.domain.PkpmOperatorStatus;


public interface ISubscriptionService {

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

	List<SubsCription> findSubsCriptionByProductName(Integer userId);


	/*Integer selectProductCountByAdId();*/
}
