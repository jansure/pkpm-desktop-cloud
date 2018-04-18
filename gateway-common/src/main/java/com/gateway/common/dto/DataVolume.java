package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataVolume implements Serializable {
    private String type;
    private Integer size;
}
