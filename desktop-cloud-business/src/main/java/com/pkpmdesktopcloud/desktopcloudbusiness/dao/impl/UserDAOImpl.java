package com.pkpmdesktopcloud.desktopcloudbusiness.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pkpmdesktopcloud.desktopcloudbusiness.dao.UserDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper.UserMapper;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;

import javax.annotation.Resource;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Resource
	private UserMapper userMapper;
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userInfo  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.UserDAO#saveUserInfo(com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo)  
	 */  
	@Override
	public void saveUserInfo(UserInfo userInfo) {
		
		userInfo.setUserID(null);
		userMapper.insert(userInfo);
	}


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userName
	 * @param userMobileNumber
	 * @param userEmail
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.UserDAO#findByUserNameOrTelephoneOrUserEmail(java.lang.String, java.lang.String, java.lang.String)  
	 */  
	@Override
	public UserInfo findByUserNameOrTelephoneOrUserEmail(String userName, String userMobileNumber, String userEmail) {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		List<UserInfo> list = userMapper.findUserInfoList(userInfo);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserMobileNumber(userMobileNumber);
		list = userMapper.findUserInfoList(userInfo);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		userInfo = new UserInfo();
		userInfo.setUserEmail(userEmail);
		list = userMapper.findUserInfoList(userInfo);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userInfo
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.UserDAO#updateUserInfo(com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo)  
	 */  
	@Override
	public int updateUserInfo(UserInfo userInfo) {
		
		return userMapper.update(userInfo);
	}


	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userID
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.dao.UserDAO#getUserById(int)  
	 */  
	@Override
	public UserInfo getUserById(int userID) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserID(userID);
		
		List<UserInfo> list = userMapper.findUserInfoList(userInfo );
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

}
