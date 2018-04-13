package com.desktop.utils;

import com.pkpm.httpclientutil.common.HttpHeader;
import org.apache.http.Header;

public class HttpHeaderBulder {
    public static Header[] buildHttpHeader(String token) {
        return HttpHeader.custom().contentType("application/json").other("X-Auth-Token", token).build();
    }
}
