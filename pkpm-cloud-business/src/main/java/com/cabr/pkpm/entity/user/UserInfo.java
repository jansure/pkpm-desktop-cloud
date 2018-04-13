package com.cabr.pkpm.entity.user;

import java.io.Serializable;
import java.util.Date;

import com.cabr.pkpm.entity.component.ComponentInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j  
@Data 
public class UserInfo implements Serializable{
	
	private Integer userID;
	private Integer userAccountID;
	private String 	userName;
	private String 	userLoginPassword;
	private Integer 	userType;
	private String 	userMobileNumber;
	private String 	userEmail;
	private String 	userIdentificationCard;
	private Date 	userCreateTime;
	private String 	userIdentificationName;
	private String 	userOrganization;
	private String userArea;
	private String checkCode; 
	
	
	
	
}
