package com.gatewayserver.gatewayserver.dto.restart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RebootType implements Serializable {
    /**
     *
     */
    private Reboot reboot;
}
