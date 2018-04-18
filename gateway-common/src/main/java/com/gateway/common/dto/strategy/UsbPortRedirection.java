package com.gateway.common.dto.strategy;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsbPortRedirection implements Serializable {
    private boolean enable;

    private Options options;
}
