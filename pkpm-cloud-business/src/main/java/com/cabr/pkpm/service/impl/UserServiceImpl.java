package com.cabr.pkpm.service.impl;

import com.alibaba.fastjson.JSON;
import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.mapper.user.UserMapper;
import com.cabr.pkpm.service.IUserService;
import com.cabr.pkpm.utils.Base64Utils;
import com.cabr.pkpm.utils.ResultObject;
import com.cabr.pkpm.utils.StringUtil;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.BeanUtil;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.user.UserInfoForChangePassword;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.host}")
    private String serverHost;

    @Override
    @Transactional
    public void saveUserInfo(UserInfo userInfo) {
        userMapper.saveUserInfo(userInfo);
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {

        if (userMapper.updateUserInfo(userInfo) > 0) {
            stringRedisTemplate.opsForValue().set("user:" + userInfo.getUserID(), JSON.toJSONString(userInfo));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserInfo findUser(Integer userID) {

        // 若不存在对应的Redis缓存，从数据库查询
			UserInfo userInfo = userMapper.getUserById(userID);
			return userInfo;
    //    String str = stringRedisTemplate.opsForValue().get("user:" + userID);
        // 若存在Redis缓存，从缓存中读取
//        if (StringUtils.isNotBlank(str)) {
//
//            UserInfo userInfo = JsonUtil.deserializeEx(str, UserInfo.class);
//            return userInfo;
//        } else {
//            // 若不存在对应的Redis缓存，从数据库查询
//            UserInfo userInfo = userMapper.getUserById(userID);
//            //String jsonString = JSON.toJSONString(userInfo);
//            // 写入Redis缓存
////			stringRedisTemplate.opsForValue().set("user:"+userID, JSON.toJSONString(userInfo));
////			return userInfo;
//            stringRedisTemplate.opsForValue().set("user:" + userID, JsonUtil.serializeEx(userInfo));
//            return userInfo;
//        }

    }

    @Override
    public UserInfo findByUserNameOrTelephoneOrUserEmail(String name) {

        return userMapper.findByUserNameOrTelephoneOrUserEmail(name, name, name);
    }

    @Override
    @Transactional
    //0428 新增事务管理，若gateway请求失败，则数据库回滚，保证密码一致性
    public String changeUserPassword(UserInfoForChangePassword newUserInfo, List<SubsCription> subsList) {

        Integer userID = newUserInfo.getUserId();
        String oldPassword = newUserInfo.getOldPassword();
        String newPassword = newUserInfo.getNewPassword();

        Preconditions.checkNotNull(newPassword, "密码不能设置为空");

        UserInfo userInfo = userMapper.getUserById(userID);

        String userName = userInfo.getUserName();
        Preconditions.checkArgument(StringUtil.checkPassword(userName, newPassword), "您输入的密码不合法,请重新输入!");

        // 从数据库中查出password并解密
        String password = userInfo.getUserLoginPassword();
        String realPassword = Base64Utils.stringFromB64(password);

        Preconditions.checkArgument(realPassword.equals(oldPassword), "原密码输入错误");
        Preconditions.checkArgument(newPassword.equals(StringUtils.deleteWhitespace(newPassword)));
        Preconditions.checkArgument(!oldPassword.equals(newPassword), "密码与原密码相同");


        String encryptedPassword = Base64Utils.b64FromString(newPassword);
        userInfo.setUserLoginPassword(encryptedPassword);
        userMapper.updateUserInfo(userInfo);

        try {

            String url = serverHost + "/ad/user/update";
            CommonRequestBean requestBean = new CommonRequestBean();
            requestBean.setUserName(userName);
            for (SubsCription subInfo : subsList) {
                BeanUtil.copyPropertiesIgnoreNull(subInfo,requestBean);
                requestBean.setUserLoginPassword(newPassword);
                String jsonStr = JsonUtil.serialize(requestBean);

                String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(url, jsonStr,5, "utf-8", 100000).method(HttpMethods.POST));
                //检查与gateway连接状态
                MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
                Integer statusCode = myHttpResponse.getStatusCode();
                Preconditions.checkArgument(statusCode==HttpStatus.OK.value(),"http请求失败 Code="+statusCode);
                //检查AD修改密码服务返回状态
                String body = myHttpResponse.getBody();
                ResultObject resultObject = JsonUtil.deserialize(body, ResultObject.class);
                Integer gatewayCode = resultObject.getCode();
                Preconditions.checkArgument(gatewayCode== HttpStatus.OK.value(),"AD域修改失败 Code="+gatewayCode);
                log.info("密码修改成功 --adId={}",subInfo.getAdId());
            }
            return "密码修改成功";
        } catch (HttpProcessException e) {
            throw Exceptions.newBusinessException("http建立连接失败,请确认GateWay服务是否开启");
        }
    }

}
