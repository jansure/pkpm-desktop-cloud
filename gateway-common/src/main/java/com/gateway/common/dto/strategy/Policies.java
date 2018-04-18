package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Policies implements Serializable {

    @JsonProperty(value = "clipboard_redirection")
    private String clipboardRedirection;

    @JsonProperty(value = "file_redirection")
    private FileRedirection fileRedirection;

    @JsonProperty(value = "hdp_plus")
    private HdpPlus hdpPlus;

    @JsonProperty(value = "printer_redirection")
    private PrinterRedirection printerRedirection;

    @JsonProperty(value = "usb_port_redirection")
    private UsbPortRedirection usbPortRedirection;

}
