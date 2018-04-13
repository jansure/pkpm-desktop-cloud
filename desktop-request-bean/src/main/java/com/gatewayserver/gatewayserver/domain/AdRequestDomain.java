package com.gatewayserver.gatewayserver.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdRequestDomain implements Serializable {
    //fixme 完善 @xuhe
    private String adminName;
    private String password;
    private String hostName;
    private String ipAddress;
    private String url;
    private String ou;
}
