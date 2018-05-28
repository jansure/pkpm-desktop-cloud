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
	private PkpmCloudSubscriptionMapper subscriptionMapper;
	
	/**
	 * 根据用户id获取订单Id
	 * @param userId 用户id
	 * @return
	 * 
	 */
	@Override
	public List<Long> findSubsId(int userId) {
		PkpmCloudSubscription subsCription = new PkpmCloudSubscription();
		subsCription.setUserId(userId);
		
		List<PkpmCloudSubscription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		if(list != null && list.size() > 0) {
			return list.stream().map(PkpmCloudSubscription::getSubsId).collect(Collectors.toList());
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
	public List<PkpmCloudSubscription> findSubsCriptionByUserId(int userId){
		PkpmCloudSubscription subsCription = new PkpmCloudSubscription();
		subsCription.setUserId(userId);
		
		List<PkpmCloudSubscription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		return list;
	}

	@Override
	public Integer updateSubsCriptionBySubsId(PkpmCloudSubscription subsCription) {
		
		int num = 0;
		
		if(subsCription == null) {
			return num;
		}
		
		//先获取相关信息
		PkpmCloudSubscription subs = new PkpmCloudSubscription();
		subs.setSubsId(subsCription.getSubsId());
		subs.setDesktopId(subsCription.getDesktopId());
		List<PkpmCloudSubscription> list = subscriptionMapper.getSubsCriptionList(subs);
		
		//批量更新
		if(list != null && list.size() > 0) {
			for(PkpmCloudSubscription subsCriptionItem : list) {
				
				subs.setId(subsCriptionItem.getId());
				subs.setStatus(subsCription.getStatus());
				num += subscriptionMapper.update(subs);
			}
		}
		
		return num;
	}

	@Override
	public Integer saveSubscription(PkpmCloudSubscription subscription) {
		
		subscription.setId(null);
		return subscriptionMapper.insert(subscription);
	}

	@Override
	public Integer selectCount(Integer userId,  String status) {
		PkpmCloudSubscription subsCription = new PkpmCloudSubscription();
		subsCription.setUserId(userId);
		subsCription.setStatus(status);
		
		List<PkpmCloudSubscription> list = subscriptionMapper.getSubsCriptionList(subsCription);
		if(list != null) {
			return list.size();
		}
		
		return 0;
	}
//查询非失败订单并自增
	@Override
	public Integer countByAdIdExceptFailedSubs(Integer adId) {
		int count=0;
		PkpmCloudSubscription subscription = new PkpmCloudSubscription();
		subscription.setAdId(adId);
		List<PkpmCloudSubscription> invalidSubs = subscriptionMapper.countSubsExceptFailed(subscription);
		count+= invalidSubs.size();
		return count;
	}

	@Override
	public Integer selectTotalById(Integer userId) {
		
		return selectCount(userId, null);
	}

}
