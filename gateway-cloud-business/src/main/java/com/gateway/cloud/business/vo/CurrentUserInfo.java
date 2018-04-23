package com.gateway.cloud.business.vo;

import com.gateway.cloud.business.entity.ComponentInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class CurrentUserInfo {
	private Integer userId;
    private String userName;

    
}
