package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Link implements Serializable {
    private String rel;
    private String hRel;
}
