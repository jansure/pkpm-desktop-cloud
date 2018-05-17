package com.pkpmcloud.fileserver.VO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pkpmcloud.fileserver.domain.PkpmFileInfo;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PkpmFileInfoVO {
	 /** 组名*/
     private Integer groupName;
     
     /** localdatetime 转换成 'yyyy年MM月dd日HH时mm分ss秒'的格式*/
     private String createTime;
     
     /** 文件长度*/
     private String fileSize;
     /**原文件名 */
     private String originFileName;
     /**目标文件名 */
     private String destFileName;
     
}
