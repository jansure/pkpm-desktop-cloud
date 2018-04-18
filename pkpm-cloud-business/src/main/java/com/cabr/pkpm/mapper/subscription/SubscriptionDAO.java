package com.cabr.pkpm.mapper.subscription;

import com.cabr.pkpm.entity.subscription.SubsCription;

import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/4/17
 */
public interface SubscriptionDAO {
    SubsCription selectBySubId(Long subId);
}
