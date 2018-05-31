package com.pkpmdesktopcloud.mediacontentserver.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StorePath implements Serializable {

    private String group;

    private String path;
    
    private String fullPath;
    
}
