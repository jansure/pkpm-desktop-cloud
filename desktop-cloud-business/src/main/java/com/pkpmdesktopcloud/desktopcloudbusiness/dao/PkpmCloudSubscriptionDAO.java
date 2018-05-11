package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;

public interface PkpmCloudSubscriptionDAO {
	
	/**
	 * 根据用户id获取订单Id
	 * @param userId 用户id
	 * @return
	 * 
	 */
	List<Long> findSubsId(int userId);

	/**
	 * 根据用户Id查Subscription
	 * 
	 * @param userId
	 * @return
	 */
	List<PkpmCloudSubscription> findSubscriptionByUserId(int userId);

	Integer updateSubscriptionBySubsId(PkpmCloudSubscription Subscription);

	Integer saveSubscription(PkpmCloudSubscription Subscription);

	Integer selectCount(Integer userId,  String status);
	
	Integer selectTotalById(Integer userId);

}
