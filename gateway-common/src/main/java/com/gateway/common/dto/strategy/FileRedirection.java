package com.gateway.common.dto.strategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FileRedirection implements Serializable {

	
    private Options options;

    //文件重定向。取值为：DISABLED：表示禁用。READ_ONLY：表示只读。READ_AND_WRITE：表示读写。
    @JsonProperty(value = "redirection_mode")
    private String redirectionMode;
}
