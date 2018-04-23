package com.cabr.pkpm.mapper.user;

import org.apache.ibatis.annotations.Mapper;

import com.cabr.pkpm.entity.user.UserInfo;

@Mapper
public interface UserMapper {

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
