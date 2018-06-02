package com.pkpmcloud.constants;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
public class RequestConst {

    public static final String authJson = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"name\":\"{adminName}\",\"password\":\"{adminPassword}\",\"domain\":{\"name\":\"{adminName}\"}}}},\"scope\":{\"project\":{\"id\":\"{projectId}\"}}}}";
    public static final String shutdownJson="{\"os-stop\":null}";
}
