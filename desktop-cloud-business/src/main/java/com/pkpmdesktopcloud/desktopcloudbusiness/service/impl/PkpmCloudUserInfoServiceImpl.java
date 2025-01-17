package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desktop.utils.Base64Util;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.StringUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.BeanUtil;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.user.UserInfoForChangePassword;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudUserInfoDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudUserInfoService;
import com.pkpmdesktopcloud.redis.RedisCache;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PkpmCloudUserInfoServiceImpl implements PkpmCloudUserInfoService {
	
	private static final String USER_ID = "user";

    @Resource
    private PkpmCloudUserInfoDAO userDAO;

    @Value("${server.host}")
    private String serverHost;

    @Override
    @Transactional
    public void saveUserInfo(PkpmCloudUserInfo userInfo) {
        userDAO.saveUserInfo(userInfo);
    }

    @Override
    public boolean updateUserInfo(PkpmCloudUserInfo userInfo) {

        if (userDAO.updateUserInfo(userInfo) > 0) {
        	
        	RedisCache cache = new RedisCache(USER_ID);
        	cache.putObject(userInfo.getUserId(), userInfo);
            
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PkpmCloudUserInfo findUser(Integer userID) {

        // 若不存在对应的Redis缓存，从数据库查询
			PkpmCloudUserInfo userInfo = userDAO.getUserById(userID);
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
    public PkpmCloudUserInfo findByUserNameOrTelephoneOrUserEmail(String name) {

        return userDAO.findByUserNameOrTelephoneOrUserEmail(name, name, name);
    }



    @Override
    public String changeUserPassword(UserInfoForChangePassword newUserInfo, List<PkpmCloudSubscription> subsList) {
        Integer userID = newUserInfo.getUserId();
        String oldPassword = newUserInfo.getOldPassword();
        String newPassword = newUserInfo.getNewPassword();

        Preconditions.checkNotNull(newPassword, "密码不能设置为空");

        PkpmCloudUserInfo userInfo = userDAO.getUserById(userID);

        String userName = userInfo.getUserName();
        Preconditions.checkArgument(!StringUtil.checkPassword(userName, newPassword), "您输入的密码不合法,请重新输入!");

        // 从数据库中查出password并解密
        String password = userInfo.getUserLoginPassword();
        String realPassword = Base64Util.stringFromB64(password);

        Preconditions.checkArgument(!realPassword.equals(oldPassword), "原密码输入错误");
        Preconditions.checkArgument(!newPassword.equals(StringUtils.deleteWhitespace(newPassword)));
        Preconditions.checkArgument(oldPassword.equals(newPassword), "密码与原密码相同");


        String encryptedPassword = Base64Util.b64FromString(newPassword);
        userInfo.setUserLoginPassword(encryptedPassword);
        userDAO.updateUserInfo(userInfo);

        try {

            String url = serverHost + "/ad/user/update";
            CommonRequestBean requestBean = new CommonRequestBean();
            requestBean.setUserName(userName);
            for (PkpmCloudSubscription subInfo : subsList) {
                BeanUtil.copyPropertiesIgnoreNull(subInfo,requestBean);
                requestBean.setUserLoginPassword(newPassword);
                String jsonStr = JsonUtil.serialize(requestBean);

                String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(url, jsonStr,5, "utf-8", 100000).method(HttpMethods.POST));
                MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);

                Integer statusCode = myHttpResponse.getStatusCode();
                String body = myHttpResponse.getBody();
                if (statusCode!= HttpStatus.OK.value()){
                    throw Exceptions.newBusinessException("AD域密码修改失败");
                }
                log.info("密码修改成功 --adId={}",subInfo.getAdId());
            }
            return "密码修改成功";
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        throw Exceptions.newBusinessException("密码修改失败");
    }

    /**
     * 过滤查询
     * @param userInfo
     * @return
     */
    public List<PkpmCloudUserInfo> userList(PkpmCloudUserInfo userInfo) {

        List<PkpmCloudUserInfo> userInfoList = userDAO.userList(userInfo);
        return userInfoList;

    }

    /**
     * 默认查询
     * @return
     */
    public List<PkpmCloudUserInfo> userList() {

        List<PkpmCloudUserInfo> userInfoList = userDAO.userList();
        return userInfoList;

    }
    
}
