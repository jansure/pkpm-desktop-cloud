package com.gatewayserver.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities implements Serializable {
    // 桌面id
    @JsonProperty(value = "desktop_id")
    private String desktopId;
}
