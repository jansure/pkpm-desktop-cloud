package com.cabr.pkpm.mapper.subscription;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cabr.pkpm.entity.subscription.SubsCription;
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
	 * 根据用户Id查subscription
	 * 
	 * @param userId
	 * @return
	 */
	List<SubsCription> findSubsCriptionByUserId(int userId);

	Integer updateSubsCriptionBySubsId(SubsCription subsCription);

	Integer saveSubscription(SubsCription subscription);

	Integer selectCount(@Param("userId") Integer userId, @Param("status") String status);

}
