package com.gatewayserver.gatewayserver.domain;

import lombok.Data;

@Data
public class AdDomain {
    private String adIpAddress;
    private String adHostName;
    private Integer adPort;
    private String adAdminName;
    private String adAdminPassword;
    private String adOu;
    private String adName;
    private String caUrl;
}
