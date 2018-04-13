package com.cabr.pkpm.utils.httpclient;

import org.apache.http.Header;

public class HttpResponse {

    private String body;
    private Integer statusCode;
    private Header[] headers;
    private String reasonPhrase;

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public HttpResponse setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public HttpResponse setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public HttpResponse setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
            "body='" + body + '\'' +
            ", statusCode=" + statusCode +
            '}';
    }
}
