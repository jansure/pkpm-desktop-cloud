package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubscriptionDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.SubscriptionMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsCription;

@Repository
public class SubscriptionDAOImpl implements SubscriptionDAO{
	
	@Autowired
	private SubscriptionMapper subscriptionMapper;
	
	/**
	 * 根据用户id获取订单Id
	 * @param userId 用户id
	 * @return
	 * 
	 */
	@Override
	public List<Long> findSubsId(int userId) {
		SubsCription subsCription = new SubsCription();
		subsCription.setUserId(userId);
		
		List<SubsCription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		if(list != null && list.size() > 0) {
			return list.stream().map(SubsCription::getSubsId).collect(Collectors.toList());
		}
		
		return null;
	}

	/**
	 * 根据用户Id查subscription
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<SubsCription> findSubsCriptionByUserId(int userId){
		SubsCription subsCription = new SubsCription();
		subsCription.setUserId(userId);
		
		List<SubsCription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		return list;
	}

	@Override
	public Integer updateSubsCriptionBySubsId(SubsCription subsCription) {
		
		int num = 0;
		
		if(subsCription == null) {
			return num;
		}
		
		//先获取相关信息
		SubsCription subs = new SubsCription();
		subs.setSubsId(subsCription.getSubsId());
		List<SubsCription> list = subscriptionMapper.getSubsCriptionList(subs);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(SubsCription subsCriptionItem : list) {
				
				subs.setId(subsCriptionItem.getId());
				subs.setStatus(subsCription.getStatus());
				num += subscriptionMapper.update(subs);
			}
		}
		
		return num;
	}

	@Override
	public Integer saveSubscription(SubsCription subscription) {
		
		subscription.setId(null);
		return subscriptionMapper.insert(subscription);
	}

	@Override
	public Integer selectCount(Integer userId,  String status) {
		SubsCription subsCription = new SubsCription();
		subsCription.setUserId(userId);
		subsCription.setStatus(status);
		
		List<SubsCription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		if(list != null) {
			return list.size();
		}
		
		return 0;
	}
	
	@Override
	public Integer selectTotalById(Integer userId) {
		
		return selectCount(userId, null);
	}

}
