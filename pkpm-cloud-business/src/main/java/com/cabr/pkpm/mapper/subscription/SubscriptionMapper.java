package com.cabr.pkpm.mapper.subscription;

import java.util.List;

import com.cabr.pkpm.entity.subscription.Subscription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscriptionMapper {
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
	List<Subscription> findSubscriptionByUserId(int userId);

	Integer updateSubscriptionBySubsId(Subscription Subscription);

	Integer saveSubscription(Subscription Subscription);

	Integer selectCount(@Param("userId") Integer userId, @Param("status") String status);

	Integer selectProductCountByAdId(@Param("productId") Integer productId,@Param("adId") Integer adId);

	Integer selectProductCountByProjectId(@Param("productId") Integer productId,@Param("projectId") String projectId);

	Integer selectTotalById(@Param("userId") Integer userId);

	Subscription selectSubscriptionBySubsId(Long subsId);

}
