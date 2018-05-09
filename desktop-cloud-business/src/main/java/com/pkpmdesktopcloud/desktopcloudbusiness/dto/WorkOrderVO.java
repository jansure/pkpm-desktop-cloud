package com.pkpmdesktopcloud.desktopcloudbusiness.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class WorkOrderVO {
	
	private Integer userId;
	private Integer productId;
	private Integer regionId;
	private Integer	hostConfigId;
	private Integer cloudStorageTimeId;
	
}