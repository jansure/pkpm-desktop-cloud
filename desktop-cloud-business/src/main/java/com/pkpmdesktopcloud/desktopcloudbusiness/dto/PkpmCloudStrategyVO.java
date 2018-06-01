package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data 
public class PkpmCloudStrategyVO {
	
	//用户Id
	private Integer userId;
	
	//用户名
    private String userName;
    
    //用户企业
    private String userOrganization;
    
    //usb是否开启（0：未开启，1：已开启）
    private Integer usb;
    
    //打印机是否开启（0：未开启，1：已开启）
    private Integer printer;
}
