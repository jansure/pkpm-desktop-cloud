package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PkpmCloudAccount {
	
	/** 账户ID */
	private Integer accountId;

	/** 账� */
	private Integer accountType;

	/** 账户名称 */
	private String accountName;

	/** 账户说明 */
	private String accountDesc;

	/** 账户余额 */
	private String accountBalance;

	/** 账户所属区域 */
	private String accountArea;

}