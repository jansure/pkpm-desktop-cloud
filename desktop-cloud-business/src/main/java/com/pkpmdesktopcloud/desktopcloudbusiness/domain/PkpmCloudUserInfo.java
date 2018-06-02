package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class PkpmCloudUserInfo implements Serializable{
	
	/** 用户ID*/
    private Integer userId;

    /** 手机号码*/
    private String userMobileNumber;

    /** 用户账号所属账号ID*/
    private Integer userAccountId;

    /** 用户名称*/
    private String userName;

    /** 用户登录密码*/
    private String userLoginPassword;

    /** 1、�*/
    private Integer userType;

    /** */
    private String userEmail;

    /** 用户身份证号码*/
    private String userIdentificationCard;

    /** 用户创建时间*/
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime userCreateTime;

    /** 用户身份证姓名*/
    private String userIdentificationName;

    /** 用户所属组织名称*/
    private String userOrganization;

    /** 用户状态*/
    private String userStatus;

    /** 用户昵称*/
    private String userNickname;

    /** 用户所属区域*/
    private String userArea;
    
//	private String checkCode;
}
