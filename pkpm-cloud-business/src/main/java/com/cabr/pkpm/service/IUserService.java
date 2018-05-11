package com.cabr.pkpm.service;

import com.cabr.pkpm.entity.Subscription.Subscription;
import com.cabr.pkpm.entity.user.UserInfo;
import com.gateway.common.dto.user.UserInfoForChangePassword;

import java.util.List;

public interface IUserService {

	void saveUserInfo(UserInfo userInfo);
	
	boolean updateUserInfo(UserInfo userInfo);

	/**
	 * @return
	 * @author xuhe
	 */
	String changeUserPassword(UserInfoForChangePassword newUserInfo, List<Subscription> subsList);
	
	public UserInfo findUser(Integer userID);

	UserInfo findByUserNameOrTelephoneOrUserEmail(String name);

}
