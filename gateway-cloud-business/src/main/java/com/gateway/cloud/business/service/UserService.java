package com.gateway.cloud.business.service;

import com.gateway.cloud.business.entity.SubsCription;
import com.gateway.cloud.business.entity.UserInfo;
import com.gateway.common.dto.user.UserInfoForChangePassword;

import java.util.List;

public interface UserService {

	void saveUserInfo(UserInfo userInfo);
	
	boolean updateUserInfo(UserInfo userInfo);

	/**
	 * @return
	 * @author xuhe
	 */
	String changeUserPassword(UserInfoForChangePassword newUserInfo, List<SubsCription> subsList);
	
	public UserInfo findUser(Integer userID);

	UserInfo findByUserNameOrTelephoneOrUserEmail(String name);

}
