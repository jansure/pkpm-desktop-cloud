package com.cabr.pkpm.mapper.subscription;

import com.cabr.pkpm.entity.subscription.SubsCription;
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
    public void updateSubsCriptionBySubsId() throws Exception {
        SubsCription subsCription = new SubsCription();
        subsCription.setSubsId(152403125739259L);
        subsCription.setStatus("Invalid");
        Integer result = mapper.updateSubsCriptionBySubsId(subsCription);
        System.out.println(result);
    }

    @Test
    public void  selectProductCountByAdId(){
        int count = mapper.selectProductCountByAdId(1, 1);
        System.out.println(count);
    }

}