package com.pkpm.httpclientutil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author wangxiulong
 * @ClassName: HuaWeiResponse
 * @Description: 华为接口返回对象
 * @date 2018年4月9日
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuaWeiResponse {

    private String job_id;
    private String status;
    private Desktop desktop;
    private String ext1;//扩展使用

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Desktop {

        private String desktop_id;
        private String computer_name;
        private String status;
        private String error_code;
        private String error_msg;

    }

}


