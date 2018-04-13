package com.desktop.utils;

import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.SSLs;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

-client-util.builder.HCB;

public class HttpConfigBuilder {
    public static HttpConfig buildHttpConfig(String url,
                                             String strJson,
                                             String token,
                                             Integer tryTimes,
                                             String encode,
                                             Integer timeOut)
            throws HttpProcessException {
        Header[] headers = HttpHeaderBulder.buildHttpHeader(token);
        HCB hcb = HCB.custom().timeout(timeOut)
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)
                .ssl()
                .retry(tryTimes);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom().headers(headers, true)
                .client(client).url(url)
                .json(strJson)
                .encoding("utf-8");
        return config;
    }

    public static HttpConfig buildHttpConfig(String url,
                                             String token,
                                             Integer tryTimes,
                                             String encode,
                                             Integer timeOut)
            throws HttpProcessException {
        Header[] headers = HttpHeaderBulder.buildHttpHeader(token);
        HCB hcb = HCB.custom().timeout(timeOut)
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)
                .ssl()
                .retry(tryTimes);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom().headers(headers, true)
                .client(client).url(url)
                .encoding("utf-8");
        return config;
    }
}
