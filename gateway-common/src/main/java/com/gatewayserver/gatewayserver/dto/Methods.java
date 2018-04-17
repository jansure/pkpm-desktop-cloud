package com.gatewayserver.gatewayserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Methods implements Serializable {
    private List<String> password;
}
