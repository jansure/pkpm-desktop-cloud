package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import lombok.Data;

@Data 
public class PkpmCloudStrategyVO {
	
	//用户Id
	private Integer userId;
	
	//用户名
    private String userName;
    
    //用户企业
    private String userOrganization;
    
    //usb是否开启。取值为：false：表示关闭。true：表示开启。
    private boolean usbEnable;
    
    //打印是否开启。取值为：false：表示关闭。true：表示开启。
    private boolean printerEnable;
    
    //文件重定向是否开启。取值为：false：表示关闭。true：表示开启。
    private boolean fileEnable;
    
    //文件重定向是否开启。取值为：false：表示关闭。true：表示开启。
    private boolean clipboardEnable;
    
    //是否开启HDP Plus。取值为：false：表示关闭。true：表示开启。
    private boolean hdpPlusEnable;
}
