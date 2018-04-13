package com.gatewayserver.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private String name;
    private String password;
    private Domain domain;
}
