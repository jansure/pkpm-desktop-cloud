package com.cabr.pkpm.dao.impl;

import com.cabr.pkpm.dao.SubscriptionDAO;
import com.cabr.pkpm.dao.mapper.SubscriptionMapper;
import com.cabr.pkpm.entity.subscription.SubsCription;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuhe
 * @description
 * @date 2018/4/17
 */
public class SubscriptionDAOImpl implements SubscriptionDAO {

    @Resource
    SubscriptionMapper mapper;

    /**
     * xuhe
     */
    @Override
    public SubsCription selectBySubId(Long subId) {
        SubsCription subsCription= new SubsCription();
        subsCription.setSubsId(subId);
        return mapper.select(subsCription).get(0);
    }
}
