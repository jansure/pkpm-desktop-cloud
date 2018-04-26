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
	private String invalidtime;
	private boolean flagTime;
	private String hostIp;
	private Integer status;
}
