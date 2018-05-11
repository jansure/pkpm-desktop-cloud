package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubscriptionDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.PkpmCloudSubscriptionMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;

import javax.annotation.Resource;

@Repository
public class PkpmCloudSubscriptionDAOImpl implements PkpmCloudSubscriptionDAO{
	
	@Resource
	private PkpmCloudSubscriptionMapper SubscriptionMapper;
	
	/**
	 * 根据用户id获取订单Id
	 * @param userId 用户id
	 * @return
	 * 
	 */
	@Override
	public List<Long> findSubsId(int userId) {
		PkpmCloudSubscription Subscription = new PkpmCloudSubscription();
		Subscription.setUserId(userId);
		
		List<PkpmCloudSubscription> list = SubscriptionMapper.getSubscriptionList(Subscription);
		if(list != null && list.size() > 0) {
			return list.stream().map(PkpmCloudSubscription::getSubsId).collect(Collectors.toList());
		}
		
		return null;
	}

	/**
	 * 根据用户Id查Subscription
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<PkpmCloudSubscription> findSubscriptionByUserId(int userId){
		PkpmCloudSubscription Subscription = new PkpmCloudSubscription();
		Subscription.setUserId(userId);
		
		List<PkpmCloudSubscription> list = SubscriptionMapper.getSubscriptionList(Subscription);
		return list;
	}

	@Override
	public Integer updateSubscriptionBySubsId(PkpmCloudSubscription Subscription) {
		
		int num = 0;
		
		if(Subscription == null) {
			return num;
		}
		
		//先获取相关信息
		PkpmCloudSubscription subs = new PkpmCloudSubscription();
		subs.setSubsId(Subscription.getSubsId());
		List<PkpmCloudSubscription> list = SubscriptionMapper.getSubscriptionList(subs);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(PkpmCloudSubscription SubscriptionItem : list) {
				
				subs.setId(SubscriptionItem.getId());
				subs.setStatus(Subscription.getStatus());
				num += SubscriptionMapper.update(subs);
			}
		}
		
		return num;
	}

	@Override
	public Integer saveSubscription(PkpmCloudSubscription Subscription) {
		
		Subscription.setId(null);
		return SubscriptionMapper.insert(Subscription);
	}

	@Override
	public Integer selectCount(Integer userId,  String status) {
		PkpmCloudSubscription Subscription = new PkpmCloudSubscription();
		Subscription.setUserId(userId);
		Subscription.setStatus(status);
		
		List<PkpmCloudSubscription> list = SubscriptionMapper.getSubscriptionList(Subscription);
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
