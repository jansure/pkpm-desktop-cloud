package com.gateway.cloud.business.vo;

import com.gateway.cloud.business.entity.ComponentInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class ComponentVO {
	private Integer productId;
    private String productName;
    private String productDesc;
    private Integer componentId;
    private String componentName;
    private Integer componentType;
    private String componentTypeName;
}
