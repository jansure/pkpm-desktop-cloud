package com.pkpmcloud.constants;

import com.pkpm.httpclientutil.common.util.PropertiesUtil;

import java.time.format.DateTimeFormatter;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
public class ApiConst {

    public static final String TOKEN = "https://iam.{areaName}.myhuaweicloud.com/v3/auth/tokens";
    public static final String WORKSPACE_PREFIX="https://workspace.{areaName}.myhuaweicloud.com/v1.0/{projectId}";
    public static final String LIST_ACTIVE_DESKTOP = "/desktops/detail?status=ACTIVE";
    public static final String LIST_LOGIN_RECORD = "/desktop-users/login-records?start_time={startTime}&computer_name={computerName}";
    public static final String SHUTDOW_DESKTOP="/desktops/{desktopId}/action";

    public static final String DATE_FORMAT="yyyy-MM-dd'T'HH:mm'Z'";
    public static final String NANO_DATE_FORMAT="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter NANO_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(NANO_DATE_FORMAT);
    public static final String CONNECTED_STATUS = "CONNECTED";
    /**
     * 配置参数默认值
     */
    public static final int RECORD_QUERY_INTERVAL = 12;
    public static final int SHUTDOWN_OVER_MINUTE=5;
    public static final int HTTP_CONNECTION_TIMEOUT=120000;
    public static final int TASK_PER_THREAD=3;
    public static final int CORE_POOL_SIZE=12;
    public static final int MAXIMUM_POOL_SIZE=5;
}
