package com.cabr.pkpm.service.impl;

import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.mapper.user.UserMapper;
import com.cabr.pkpm.utils.Base64Utils;
import com.cabr.pkpm.utils.StringUtil;
import com.google.common.base.Preconditions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xuhe
 * @description
 * @date 2018/4/28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void changeUserPassword() throws Exception {
    }
    @Test
    public void getUserPassword(){
        UserInfo userInfo = userMapper.getUserById(110);

        String userName = userInfo.getUserName();
        System.out.println(userName);
        // 从数据库中查出password并解密
        String password = userInfo.getUserLoginPassword();
        String realPassword = Base64Utils.stringFromB64(password);
        System.out.println(realPassword);
    }

}