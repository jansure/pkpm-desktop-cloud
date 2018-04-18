package com.gateway.common.dto.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {

    //镜像ID。
    @JsonProperty(value = "metering.image_id")
    private String meteringImageId;

    //镜像类型。
    @JsonProperty(value = "metering.imagetype")
    private String meteringImageType;

    //桌面资源编码
    @JsonProperty(value = "metering.resourcespeccode")
    private String meteringResourcespeccode;

    //云服务类型
    @JsonProperty(value = "metering.cloudServiceType")
    private String meteringCloudServiceType;

    //创建桌面的镜像名称
    @JsonProperty(value = "image_name")
    private String image_name;

    //桌面资源类型
    @JsonProperty(value = "metering.resourcetype")
    private String meteringResourcetype;

    //操作系统位数，32或64
    @JsonProperty(value = "os_bit")
    private String os_bit;

    //桌面所在的虚拟私有云ID
    @JsonProperty(value = "vpc_id")
    private String vpc_id;

    //操作系统类型，Linux、Windows或Others
    @JsonProperty(value = "os_type")
    private String os_type;

    //GPU类型
    @JsonProperty(value = "desktop_gpu_type")
    private String desktop_gpu_type;

    //操作系统版本
    @JsonProperty(value = "desktop_os_version")
    private String desktop_os_version;
}
