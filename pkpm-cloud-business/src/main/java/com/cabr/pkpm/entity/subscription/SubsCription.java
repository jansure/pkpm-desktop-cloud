package com.cabr.pkpm.entity.subscription;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j  
@Data 
public class SubsCription implements Serializable {
	private Integer id;
	private String areaCode;
	private Long subsId;
	private Integer userId;
	private LocalDateTime createTime;
	private String remark;
	private String payChannel;
	private Integer adId;
	private String projectId;
	private String desktopId;
	private String status;
	
	
}
