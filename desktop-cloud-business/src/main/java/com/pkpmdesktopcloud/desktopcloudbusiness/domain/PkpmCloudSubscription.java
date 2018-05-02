package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PkpmCloudSubscription implements Serializable {
	/** 订单表自增id */
	private Integer id;

	/** */
	private String areaCode;

	/** 订单id */
	private Long subsId;

	/** 用户id */
	private Integer userId;

	/** 订单创建时间 */
	private LocalDateTime createTime;

	/** 订单备注 */
	private String remark;

	/**
	 * 付款渠道 wechat alipay unipay
	 */
	private String payChannel;

	/** */
	private Integer adId;

	/** */
	private String projectId;

	/** */
	private String desktopId;

	/**
	 * INVALID 无效 VALID 有效
	 */
	private String status;

}
