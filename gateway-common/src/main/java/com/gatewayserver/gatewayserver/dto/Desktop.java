package com.gatewayserver.gatewayserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gatewayserver.gatewayserver.dto.detail.Metadata;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Desktop implements Serializable {
    // 用户名
    @JsonProperty(value = "user_name")
    private String userName;
    // 用户组
    @JsonProperty(value = "user_group")
    private String userGroup;
    // 用户邮箱
    @JsonProperty(value = "user_email")
    private String userEmail;
    // 产品套餐id
    @JsonProperty(value = "product_id")
    private String productId;
    // 镜像id
    @JsonProperty(value = "image_id")
    private String imageId;
    // 桌面名称
    @JsonProperty(value = "computer_name")
    private String computerName;
    // 桌面id
    @JsonProperty(value = "desktop_id")
    private String desktopId;
    // 创建成功时间
    @JsonProperty(value = "created")
    private String createTime;
    // private LocalDateTime createTime;
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    @JsonProperty(value = "security_groups")
    private List<SecurityGroups> securityGroups;
    // 系统盘
    @JsonProperty(value = "root_volume")
    private RootVolume rootVolume;
    // 数据盘
    @JsonProperty(value = "data_volumes")
    private List<DataVolume> dataVolumes;
    // 网卡信息
    private List<Nic> nics;
    // 可用分区
    @JsonProperty(value = "availability_zone")
    private String availabilityZone;
    // 组织单元
    @JsonProperty(value = "ou_name")
    private String ouName;
    // 桌面状态
    private String status;
    // 登录状态
    @JsonProperty(value = "login_status")
    private String loginStatus;
    //桌面ip
    @JsonProperty(value = "desktop_ip")
    private String desktopIp;
    private Metadata metadata;
    @JsonProperty(value = "ad_id")
    private Integer adId;
    private String desktopOperatorType;
    private String marker;

}
