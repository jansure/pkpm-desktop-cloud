package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import org.apache.ibatis.annotations.Mapper;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;

@Mapper
public interface UserDAO {

	void saveUserInfo(UserInfo userInfo);

	UserInfo findByUserNameOrTelephoneOrUserEmail(String userName, String userMobileNumber, String userEmail);

	/**
	 * 更新user表
	 * @param userInfo
	 *
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo); 
	/**
	 * 根据userID获取商品名
	 * @param userID 商品id
	 * @return
	 */
	public UserInfo getUserById(int userID); //通过userID获取对象

}
