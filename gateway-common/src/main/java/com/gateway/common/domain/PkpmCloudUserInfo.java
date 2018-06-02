package com.gateway.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PkpmCloudUserInfo implements Serializable {
    private Integer userId;
    private Integer userAccountId;
    private String userName;
    private String userLoginPassword;
    private Integer userType;
    private String userMobileNumber;
    private String userEmail;
    private String userIdentificationCard;
    private String userIdentificationName;
    private LocalDateTime userCreateTime;
    private String userOrganization;
    private String userExtend1;
    private String userExtend2;
    private String userArea;
    private Boolean isDelete;
    private Integer adId;
    private String projectId;

}
