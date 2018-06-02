package com.pkpmcloud.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: xuhe
 * @Date: 2018/3/7
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Desktop {

    private String desktop_id;

    private String computer_name;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date created;

    private String login_status;

    private String user_name;


}
