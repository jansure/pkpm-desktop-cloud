package com.gateway.common.dto.strategy;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Options implements Serializable {

	//-----------文件重定向-----------
    //是否开启光盘驱动器
    @JsonProperty(value = "cd_rom_drive_enable")
    private boolean cdRomDriveEnable;
    //是否开启固定驱动器
    @JsonProperty(value = "fixed_drive_enable")
    private boolean fixedDriveEnable;
    //是否开启网络驱动器
    @JsonProperty(value = "network_drive_enable")
    private boolean networkDriveEnable;
    //是否开启可移除驱动器
    @JsonProperty(value = "removable_drive_enable")
    private boolean removableDrivenEable;


    //-----------HDP Plus-----------
    //带宽,取值范围为[1000-25000]。
    private int bandwidth;
    //帧率,取值范围为[15-30]。
    @JsonProperty(value = "frame_rate")
    private int frameRate;
    //有损压缩质量,取值范围为[70-90]。
    @JsonProperty(value = "lossy_compression_quality")
    private int lossyCompressionQuality;
    //平滑系数,取值范围为[0-60]。
    @JsonProperty(value = "smoothing_factor")
    private int smoothingFactor;
    //视频帧率,取值范围为[15-50]。
    @JsonProperty(value = "video_frame_rate")
    private int videoFrameRate;

    //-----------打印机设备重定向-----------
    //是否开启同步客户端默认打印机
    @JsonProperty(value = "sync_client_default_printer_enable")
    private boolean syncClientDefaultPrinterEnable;
    
    //通用打印机驱动。取值为：
    //Default。
    //HDP XPSDrv Driver。
    //Universal Printing PCL 5。
    //Universal Printing PCL 6。
    //Universal Printing PS。
    @JsonProperty(value = "universal_printer_driver")
    private String universalPrinterDriver;

    //-----------USB端口重定向-----------
    //是否开启图像设备
    @JsonProperty(value = "usb_image_enable")
    private boolean usbImageEnable;
    //是否开启打印设备
    @JsonProperty(value = "usb_printer_enable")
    private boolean usbPrinterEnable;
    //是否开启智能卡设备
    @JsonProperty(value = "usb_smart_card_enable")
    private boolean usbSmartCardEnable;
    //是否开启存储设备
    @JsonProperty(value = "usb_storage_enable")
    private boolean usbStorageEnable;
    //是否开启视频设备
    @JsonProperty(value = "usb_video_enable")
    private boolean usbVideoEnable;

}
