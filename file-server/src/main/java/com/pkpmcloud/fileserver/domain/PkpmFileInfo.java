package com.pkpmcloud.fileserver.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmFileInfo {
	
	/** 自增主键 */
	private Integer id;
	
    /**组ID */
    private Integer groupName;

    /**文件MD5值 */
    private String md5;

    /**原文件名 */
    private String originFileName;

    /**目标文件名 */
    private String destFileName;

    /** 文件长度*/
    private String fileSize;

    /**创建日期 */
    private LocalDateTime createTime;

    /** 文件后缀*/
    private String postFix;
    
}