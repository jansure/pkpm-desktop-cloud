/**
  * Copyright 2018 bejson.com 
  */
package com.pkpmcloud.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkpmcloud.constants.ApiConst;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author xuhe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Record {


    private String user_name;

    private String connection_start_time;
    private String connection_setup_time;
    private String connection_end_time;


}