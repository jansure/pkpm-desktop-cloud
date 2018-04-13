package com.cabr.pkpm.entity.product;

import com.cabr.pkpm.entity.component.ComponentInfo;

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
