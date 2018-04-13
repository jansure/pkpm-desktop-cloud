package com.cabr.pkpm.entity.user;

import com.cabr.pkpm.entity.component.ComponentInfo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class CurrentUserInfo {
	private Integer userId;
    private String userName;

    
}
