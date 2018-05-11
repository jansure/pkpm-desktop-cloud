package com.cabr.pkpm.mapper.Subscription;

import com.cabr.pkpm.entity.Subscription.Subscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/4/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionMapperTest {
    @Autowired
    SubscriptionMapper mapper;
    /**
     *单元测试 根据subsId更新订单状态
     * @author xuhe
     */
    @Test
    public void updateSubscriptionBySubsId() throws Exception {
        Subscription Subscription = new Subscription();
        Subscription.setSubsId(152403125739259L);
        Subscription.setStatus("Invalid");
        Integer result = mapper.updateSubscriptionBySubsId(Subscription);
        System.out.println(result);
    }

    @Test
    public void  selectProductCountByAdId(){
        int count = mapper.selectProductCountByAdId(1, 1);
        System.out.println(count);
    }

}