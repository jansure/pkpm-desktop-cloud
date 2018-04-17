package com.cabr.pkpm.mapper.subscription;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cabr.pkpm.entity.subscription.SubsCription;
@Mapper
public interface SubsCriptionMapper {
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

	Integer saveSubscription(SubsCription subscription);

}
