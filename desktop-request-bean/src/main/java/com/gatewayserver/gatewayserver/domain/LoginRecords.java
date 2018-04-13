package com.gatewayserver.gatewayserver.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRecords implements Serializable {
    private String projectId;
    private String startTime;
    private String endTime;
    private String userName;
    private String computerName;
    private String terminalType;
    private Integer offset;
    private Integer limit;
}
