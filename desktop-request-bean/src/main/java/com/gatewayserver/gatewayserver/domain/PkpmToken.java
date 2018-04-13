package com.gatewayserver.gatewayserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmToken implements Serializable {
    private String areaName;
    private String projectId;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime expireTime;
    private int isValid;
}
