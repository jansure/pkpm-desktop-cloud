package com.desktop.constant;

import org.springframework.beans.factory.annotation.Value;

public class SubscriptionConstants {
	
	
	
	public static String OU_NAME = "pkpm";
	
	public static String USER_EMAIL = "glory_cloud@163.com";
	
	public static String STATUS = "VALID";
	
	public static String INVALID_STATUS = "INVALID";
	
	public static Integer DEFAULT_DATA_VLOUME_SIZE = 100;
	
	public  static String DEFAULT_PAY_CHANNEL = "0";

	//创建桌面成功
	public static Integer 	SUCCESS_STATUS = 1;

	//创建中
	public static Integer 	PROCESS_STATUS = 0;

	//创建桌面失败
	public static Integer 	FAILD_STATUS = 2;

}
