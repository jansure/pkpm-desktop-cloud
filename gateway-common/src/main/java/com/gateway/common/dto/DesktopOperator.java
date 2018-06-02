package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DesktopOperator implements Serializable {
    private String projectId;
    private String desktopId;
    private Integer operatorType;
}
