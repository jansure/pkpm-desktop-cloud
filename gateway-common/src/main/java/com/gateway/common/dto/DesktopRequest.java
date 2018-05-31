package com.gateway.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DesktopRequest implements Serializable {
    private List<Desktop> operatorStatusList;
    private Boolean emailNotification;

    //查询桌面详情,responsebody接收
    private Desktop desktop;
}
