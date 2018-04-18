package com.gateway.common.dto.desktop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description: 变更桌面规格返回体
 * @Author: xuhe
 * @Date: 2018/4/17
 */
@Data
public class DesktopSpecResponse {

    @JsonProperty(value = "job_id")
    private String jobId;

    @JsonProperty(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "error_msg")
    private String errorMsg;
}
