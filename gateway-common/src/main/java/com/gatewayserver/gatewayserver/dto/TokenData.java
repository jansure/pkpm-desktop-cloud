package com.gatewayserver.gatewayserver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenData implements Serializable {
    private Token token;
}
