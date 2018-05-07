package com.cabr.pkpm.entity;

import lombok.Data;

/**
 * 
 * @ClassName: PkpmCloudVideoCapture  
 * @Description: 视频截图表（pkpm_cloud_video_capture）对应的实体类
 * @author wangxiulong  
 * @date 2018年5月7日  
 *
 */
@Data
public class PkpmCloudVideoCapture {
    /** */
    private Integer capId;

    /** 截图图片类型*/
    private String capType;

    /** 截图图片*/
    private byte[] capValue;

}