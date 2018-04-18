package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HdpPlus implements Serializable {

    @JsonProperty(value = "display_level")
    private String displayLevel;

    @JsonProperty(value = "hdp_plus_enable")
    private boolean hdpPlusEnable;

    private Options options;
}
