package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class PkpmCloudSubsDetails implements Serializable {
	
	private Integer Id;
	
	 /** 订单表*/
    private Long subsId;

    /** 商品id*/
    private Integer productId;

    /** 订单明细表生成时间*/
    private LocalDateTime createTime;
    
	private LocalDateTime validTime;
	private LocalDateTime invalidTime;
	private Integer cloudStorageTimeId;
	
	private Integer cloudStorageTime;
	
}
