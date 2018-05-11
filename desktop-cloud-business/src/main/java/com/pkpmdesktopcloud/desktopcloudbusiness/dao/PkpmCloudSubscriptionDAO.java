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
	 * 根据用户Id查subscription
	 * 
	 * @param userId
	 * @return
	 */
	List<PkpmCloudSubscription> findSubsCriptionByUserId(int userId);

	Integer updateSubsCriptionBySubsId(PkpmCloudSubscription subsCription);

	Integer saveSubscription(PkpmCloudSubscription subscription);

	Integer selectCount(Integer userId,  String status);
	/**
	 * @author xuhe
	 *查询adid下的订单总数
	*/
	Integer countByAdId(Integer adId);
	
	Integer selectTotalById(Integer userId);

}
