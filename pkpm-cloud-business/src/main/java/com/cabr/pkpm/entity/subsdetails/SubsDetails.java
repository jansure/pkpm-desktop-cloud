package com.cabr.pkpm.entity.subsdetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data
public class SubsDetails implements Serializable {
	
	private Integer Id;
	private Long subsId;
	private Integer productId;
	private LocalDateTime createTime;
	private LocalDateTime validTime;
	private LocalDateTime invalidTime;
	private Integer cloudStorageTimeId;
	
	private Integer cloudStorageTime;
	
}
