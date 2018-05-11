package com.pkpmcloud.fileserver.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PkpmFileInfo {
	
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
    private Date createTime;

    /** 文件后缀*/
    private String postfix;

}