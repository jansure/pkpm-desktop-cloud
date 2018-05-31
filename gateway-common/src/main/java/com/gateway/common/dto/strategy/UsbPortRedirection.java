package com.gateway.common.dto.strategy;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsbPortRedirection implements Serializable {
    
	//是否开启USB端口重定向。取值为：false：表示关闭。true：表示开启。
	private boolean enable;

    private Options options;
}
