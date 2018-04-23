package com.gateway.cloud.business.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class ComponentInfo implements Serializable{
	
	private Integer componentId;
	private String componentName;
	private String componentDesc;
	private Integer componentType;
	private String componentTypeName;
	private String isDefault;
	
	private String hwProductId;
	
}
