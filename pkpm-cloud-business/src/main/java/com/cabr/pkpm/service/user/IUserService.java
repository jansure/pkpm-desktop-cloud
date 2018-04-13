package com.cabr.pkpm.service.user;

import com.cabr.pkpm.entity.user.UserInfo;

public interface IUserService {

	void saveUserInfo(UserInfo userInfo);
	
	boolean updateUserInfo(UserInfo userInfo);
	
	public UserInfo findUser(Integer userID);

	UserInfo findByUserNameOrTelephoneOrUserEmail(String name);

}
