package com.pkpm.fastdfs.entity;

public enum HttpStatus {
    SUCCESS(true, "成功"),
    FAIL(false, "失败");

    private boolean status;

    private String msg;

    HttpStatus(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
