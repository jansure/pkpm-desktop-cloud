/**
  * Copyright 2018 bejson.com 
  */
package com.pkpmcloud.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author xuhe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Record {


    private String user_name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime connection_start_time;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime connection_setup_time;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime connection_end_time;


}