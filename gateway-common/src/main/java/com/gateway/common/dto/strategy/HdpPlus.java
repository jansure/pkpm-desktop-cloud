package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HdpPlus implements Serializable {

	//显示级别。取值为：SMOOTHNESS_FIRST：表示流畅度优先。QUALITY_FIRST：表示画质优先。
    @JsonProperty(value = "display_level")
    private String displayLevel;

    //是否开启HDP Plus。取值为：false：表示关闭。true：表示开启。
    @JsonProperty(value = "hdp_plus_enable")
    private boolean hdpPlusEnable;

    private Options options;
}
