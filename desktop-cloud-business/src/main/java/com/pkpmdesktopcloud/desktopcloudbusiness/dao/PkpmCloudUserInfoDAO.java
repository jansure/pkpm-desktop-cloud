package com.pkpmdesktopcloud.desktopcloudbusiness.dao;

import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;

public interface PkpmCloudUserInfoDAO {

	void saveUserInfo(PkpmCloudUserInfo userInfo);

	PkpmCloudUserInfo findByUserNameOrTelephoneOrUserEmail(String userName, String userMobileNumber, String userEmail);

	/**
	 * 更新user表
	 * @param userInfo
	 *
	 * @return
	 */
	public int updateUserInfo(PkpmCloudUserInfo userInfo); 
	/**
	 * 根据userID获取商品名
	 * @param userID 商品id
	 * @return
	 */
	public PkpmCloudUserInfo getUserById(int userID); //通过userID获取对象

}
