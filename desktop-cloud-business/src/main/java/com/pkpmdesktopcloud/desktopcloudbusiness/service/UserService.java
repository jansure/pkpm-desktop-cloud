package com.pkpmdesktopcloud.desktopcloudbusiness.service;

import java.util.List;

import com.gateway.common.dto.user.UserInfoForChangePassword;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsCription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;

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
