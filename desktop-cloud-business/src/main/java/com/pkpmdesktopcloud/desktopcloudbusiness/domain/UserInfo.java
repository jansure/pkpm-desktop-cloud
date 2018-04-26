package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@Slf4j  
@Data
public class UserInfo implements Serializable{
	
	private Integer userID;
	private Integer userAccountID;
	private String 	userName;
	private String 	userLoginPassword;
	private Integer userType;
	private String 	userMobileNumber;
	private String 	userEmail;
	private String 	userIdentificationCard;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime userCreateTime;
	private String 	userIdentificationName;
	private String 	userOrganization;
	private String userArea;
	private String checkCode;
}
