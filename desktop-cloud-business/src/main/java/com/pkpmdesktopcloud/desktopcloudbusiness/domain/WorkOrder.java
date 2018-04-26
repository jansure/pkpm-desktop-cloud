package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class WorkOrder {
	private Integer id;
	private Long workId;
	private Integer userId;
	private String userName;
	private Integer productId;
	private String productName;
	private String userLoginPassWord;
	private String userMobileNumber;
	private Integer regionId;
	private String regionName;
	private Integer componentId;
	private String componentName;
	private Integer	hostConfigId;
	private String hostConfigName;
	private String hostIp;
	private Integer cloudStorageTimeId;
	private String cloudStorageTimeName;
	private Integer operateId;
	private String operateMan;
	private LocalDateTime createTime;
	private LocalDateTime openTime;
	
	private Integer status;
	
	
}
