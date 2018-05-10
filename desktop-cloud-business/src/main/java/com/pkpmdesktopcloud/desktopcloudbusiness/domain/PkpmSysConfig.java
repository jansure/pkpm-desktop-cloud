package com.pkpmdesktopcloud.desktopcloudbusiness.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * pkpm_sys_config表对应的实体类
 * 
 * @author yangpengfei
 * @date 2017/12/26
 *
 */
@Data
public class PkpmSysConfig implements Serializable{
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
