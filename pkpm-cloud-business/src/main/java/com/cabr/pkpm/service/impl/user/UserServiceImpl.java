package com.cabr.pkpm.service.impl.user;

import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.mapper.user.UserMapper;
import com.cabr.pkpm.service.user.IUserService;
import com.desktop.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {
    
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	@Transactional
	public void saveUserInfo(UserInfo userInfo) {
		userMapper.saveUserInfo(userInfo);
	}

	@Override
	public boolean updateUserInfo(UserInfo userInfo) {
		
		if(userMapper.updateUserInfo(userInfo)>0) {
			stringRedisTemplate.opsForValue().set("user:"+userInfo.getUserID(), JSON.toJSONString(userInfo));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserInfo findUser(Integer userID) {
		
			// 若不存在对应的Redis缓存，从数据库查询
//			UserInfo userInfo = userMapper.getUserById(userID);
//			return userInfo;
		String  str = stringRedisTemplate.opsForValue().get("user:"+userID);
		// 若存在Redis缓存，从缓存中读取
		if(StringUtils.isNotBlank(str)) {
			
			UserInfo userInfo = JsonUtil.deserializeEx(str, UserInfo.class);
			return userInfo;
		} else {
			// 若不存在对应的Redis缓存，从数据库查询
			UserInfo userInfo = userMapper.getUserById(userID);
			//String jsonString = JSON.toJSONString(userInfo);
			// 写入Redis缓存
//			stringRedisTemplate.opsForValue().set("user:"+userID, JSON.toJSONString(userInfo));
//			return userInfo;
			stringRedisTemplate.opsForValue().set("user:"+userID, JsonUtil.serializeEx(userInfo));
			return userInfo;
		}
		
	}

	@Override
	public UserInfo findByUserNameOrTelephoneOrUserEmail(String name) {
		
		return userMapper.findByUserNameOrTelephoneOrUserEmail(name,name,name);
	}

}
