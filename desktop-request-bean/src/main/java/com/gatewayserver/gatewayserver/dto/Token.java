package com.gatewayserver.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements Serializable {
    // 过期时间
    @JsonProperty(value = "expires_at")
    private String expiresTime;
    // 生效时间
    @JsonProperty(value = "issued_at")
    private String issuedTime;
    // 项目信息
    private Project project;
    // 用户信息
    private User user;
}
