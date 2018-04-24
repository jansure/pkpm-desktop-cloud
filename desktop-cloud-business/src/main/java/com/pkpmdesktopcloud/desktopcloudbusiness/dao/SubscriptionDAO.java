package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import java.util.List;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsCription;

public interface SubscriptionDAO {
	/**
	 * 根据用户id获取订单Id
	 * @param userId 用户id
	 * @return
	 * 
	 */
	List<Long> findSubsId(int userId);

	/**
	 * 根据用户Id查subscription
	 * 
	 * @param userId
	 * @return
	 */
	List<SubsCription> findSubsCriptionByUserId(int userId);

	Integer updateSubsCriptionBySubsId(SubsCription subsCription);

	Integer saveSubscription(SubsCription subscription);

	Integer selectCount(Integer userId,  String status);
	
	Integer selectTotalById(Integer userId);

}
