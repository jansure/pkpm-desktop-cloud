package com.pkpm.cloud.auth.server.entity;

import java.io.Serializable;
import java.util.Date;

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
	private String 	userOrginazation;
	private String userArea;
	private String checkCode; 
	
	public UserInfo() {
	}
	public UserInfo(String userName, String userLoginPassword, String userMobileNumber) {
		this.userName = userName;
		this.userLoginPassword = userLoginPassword;
		this.userMobileNumber = userMobileNumber;
	}
	
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getUserArea() {
		return userArea;
	}
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getUserAccountID() {
		return userAccountID;
	}
	public void setUserAccountID(Integer userAccountID) {
		this.userAccountID = userAccountID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLoginPassword() {
		return userLoginPassword;
	}
	public void setUserLoginPassword(String userLoginPassword) {
		this.userLoginPassword = userLoginPassword;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getUserMobileNumber() {
		return userMobileNumber;
	}
	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserIdentificationCard() {
		return userIdentificationCard;
	}
	public void setUserIdentificationCard(String userIdentificationCard) {
		this.userIdentificationCard = userIdentificationCard;
	}
	public Date getUserCreateTime() {
		return userCreateTime;
	}
	public void setUserCreateTime(Date userCreateTime) {
		this.userCreateTime = userCreateTime;
	}
	public String getUserIdentificationName() {
		return userIdentificationName;
	}
	public void setUserIdentificationName(String userIdentificationName) {
		this.userIdentificationName = userIdentificationName;
	}
	public String getUserOrginazation() {
		return userOrginazation;
	}
	public void setUserOrginazation(String userOrginazation) {
		this.userOrginazation = userOrginazation;
	}
	
	
}
