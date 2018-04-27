package com.cabr.pkpm.vo;

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
	private String adId;
	private String projectId;
}
