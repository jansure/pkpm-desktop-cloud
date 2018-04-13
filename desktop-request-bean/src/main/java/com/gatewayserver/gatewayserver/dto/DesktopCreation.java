package com.gatewayserver.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 调用创建桌面接口后返回的内容
 * @author yangpengfei
 *
 */
public class DesktopCreation implements Serializable {
    // 任务id
    @JsonProperty(value = "job_id")
    private String jobId;
    // 桌面名称
    @JsonProperty(value = "computer_name")
    private String computerName;
}
