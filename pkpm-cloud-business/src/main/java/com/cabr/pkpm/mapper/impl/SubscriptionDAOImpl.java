/*
package com.cabr.pkpm.mapper.impl;

import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.mapper.mapper.SubscriptionMapper;
import com.cabr.pkpm.mapper.subscription.SubscriptionDAO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

*/
/**
 * @author xuhe
 * @description
 * @date 2018/4/17
 *//*

@Repository
public class SubscriptionDAOImpl implements SubscriptionDAO {

    @Resource
    SubscriptionMapper mapper;

    */
/**
     * xuhe
     *//*

    @Override
    public SubsCription selectBySubId(Long subId) {
        SubsCription subsCription= new SubsCription();
        subsCription.setSubsId(subId);
        return mapper.select(subsCription).get(0);
    }
}
*/
