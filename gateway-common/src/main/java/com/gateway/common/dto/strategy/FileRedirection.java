package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FileRedirection implements Serializable {

    private Options options;

    @JsonProperty(value = "redirection_mode")
    private String redirectionMode;
}
