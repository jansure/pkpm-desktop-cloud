package com.pkpmdesktopcloud.mediacontentserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmFileConfig {
	
	/** 自增主键 */
	private Integer id;
	
    /**文件类型，IMAGE：图片、VIDEO：视频、DATUM：资料 */
    private String fileType;

    /** 文件后缀*/
    private String postFix;
    
}