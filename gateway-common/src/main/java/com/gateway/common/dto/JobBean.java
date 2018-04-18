package com.gateway.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobBean implements Serializable {
    @JsonProperty(value = "job_id")
    private String jobId;

    @JsonProperty(value = "job_type")
    private String jobType;

    @JsonProperty(value = "begin_time")
    private String beginTime;

    @JsonProperty(value = "end_time")
    private String endTime;

    private String status;

    @JsonProperty(value = "error_code")
    private String errorCode;

    @JsonProperty(value = "fail_reason")
    private String failReason;

    @JsonProperty(value = "sub_jobs_total")
    private int subJobsTotal;

    private Entities entities;

    @JsonProperty(value = "sub_jobs")
    private List<JobBean> subJobs;
}
