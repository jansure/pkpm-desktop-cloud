/**
  * Copyright 2018 bejson.com 
  */
package com.pkpmcloud.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pkpmcloud.constants.ApiConst;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author xuhe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Record {


    private String user_name;

    @JsonFormat(pattern = ApiConst.DATE_FORMAT)
    private LocalDateTime connection_start_time;
    @JsonFormat(pattern = ApiConst.DATE_FORMAT)
    private LocalDateTime connection_setup_time;
    @JsonFormat(pattern = ApiConst.DATE_FORMAT)
    private LocalDateTime connection_end_time;


}