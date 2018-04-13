package com.gatewayserver.gatewayserver.dto.strategy;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrinterRedirection implements Serializable {

    private boolean enable;

    private Options options;
}
