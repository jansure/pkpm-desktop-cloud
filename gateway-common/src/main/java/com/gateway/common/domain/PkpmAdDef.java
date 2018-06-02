package com.gateway.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PkpmAdDef implements Serializable {
    private Integer adId;
    private String adIpAddress;
    private String areaCode;
    private Integer adPort;
    private String adAdminAccount;
    private String adAdminPassword;
    private String adRoot;
    private String adDc;
    private String adOu;
    private String adGroup;
    private String adName;
    private Boolean isNotify;
    private String caUrl;
    private Integer adMaxPoolCount;
    private Integer isValid;
}