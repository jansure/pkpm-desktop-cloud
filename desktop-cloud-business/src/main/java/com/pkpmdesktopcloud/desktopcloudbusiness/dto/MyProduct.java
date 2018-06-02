package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MyProduct implements Serializable {
	private String productDesc;
	private List<String> componentName;
	private String createTime;
	private String invalidTime;
	private Boolean flagTime;
	private String hostIp;
	private Integer status;
	private Long subsId;
	private Integer adId;
	private String projectId;
	
	private String areaCode;
	private String desktopId;
	private String computerName;
	//桌面运行状态
	private String desktopStatus;
	//桌面配置信息
	private String hostConfigName;

}
