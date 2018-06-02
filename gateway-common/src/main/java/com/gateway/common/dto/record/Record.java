package com.gateway.common.dto.record;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Record implements Serializable {

    @JsonProperty(value = "computer_name")
    private String computerName;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "terminal_mac")
    private String terminalMac;

    @JsonProperty(value = "terminal_name")
    private String terminalName;

    @JsonProperty(value = "terminal_ip")
    private String terminalIp;

    @JsonProperty(value = "client_version")
    private String clientVersion;

    @JsonProperty(value = "terminal_type")
    private String terminalType;

    @JsonProperty(value = "agent_version")
    private String agentVersion;

    @JsonProperty(value = "desktop_ip")
    private String desktopIp;

    @JsonProperty(value = "connection_start_time")
    private String connectionStartTime;

    @JsonProperty(value = "connection_setup_time")
    private String connectionSetupTime;

    @JsonProperty(value = "connection_end_time")
    private String connectionEndTime;

    @JsonProperty(value = "is_reconnect")
    private boolean isReconnect;

    @JsonProperty(value = "connection_failure_reason")
    private String connectionFailureReason;

    //查询桌面用户登录记录的可选参数
    private String startTime;

    private String endTime;

    private String offset;

}
