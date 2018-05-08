package com.pkpm.fastdfs.entity;

/**
 * @param <T> 限定类型
 * @author Chenjing
 */
public class Result<T> {
    private Boolean isSuccess;

    private T data;

    private String message;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
