package com.pkpm.http-client-util;

import lombok.Data;

import java.util.Map;

@Data

public class MyHttpResponse {

    private String body;
    private Integer statusCode;
    // private List<Header> headers;
    private Map<String, Object> headers;
    private String message;
}
