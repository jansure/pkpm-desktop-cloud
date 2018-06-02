package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Policies implements Serializable {

	//剪切板重定向。取值为：
	//DISABLED：表示禁用。
	//SERVER_TO_CLIENT_ENABLED：表示开启服务端到客户端。
	//CLIENT_TO_SERVER_ENABLED：表示开启客户端到服务端。
	//TWO_WAY_ENABLED：表示开启双向。
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
