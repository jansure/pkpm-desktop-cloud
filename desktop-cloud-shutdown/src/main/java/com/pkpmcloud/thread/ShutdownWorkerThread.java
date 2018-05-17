package com.pkpmcloud.thread;

import com.desktop.utils.HttpConfigBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.constants.RequestConst;
import com.pkpmcloud.model.BootProperty;
import com.pkpmcloud.model.Desktop;
import com.pkpmcloud.model.Record;
import com.pkpmcloud.model.RecordRootBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Slf4j
public class ShutdownWorkerThread implements Runnable {


    private String token;
    private String workspaceUrlPrefix;
    private String projectDesc;
    private List<Desktop> desktops;
    private BootProperty bootProperty;

    public ShutdownWorkerThread(String token, String projectDesc, String workspaceUrlPrefix, List<Desktop> desktops, BootProperty bootProperty) {
        this.token = token;
        this.projectDesc = projectDesc;
        this.workspaceUrlPrefix = workspaceUrlPrefix;
        this.desktops = desktops;
        this.bootProperty = bootProperty;
    }

    @Override
    public void run() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        AtomicInteger order = new AtomicInteger(0);
        try {
            for (Desktop desktop : desktops) {
                String desktopId = desktop.getDesktop_id();
                String computerName = desktop.getComputer_name();

                Boolean ifShutdown = (!findLoginRecordLessThan(computerName));
                if (ifShutdown) {
                    log.info("###{}/{}-{}-发起关机请求:{}", projectDesc, Thread.currentThread().getName(), order.addAndGet(1), computerName);
                    shutDown(desktopId);
                }
            }
        } catch (HttpProcessException | IOException e) {
            log.error("!!!异常:" + e.getMessage());
        }
    }


    private Boolean findLoginRecordLessThan(String computerName) throws HttpProcessException, IOException {
        ZoneId utc = ZoneId.of("UTC");
        ZoneId local = ZoneId.of("UTC+8");
        ZonedDateTime startDateTime = ZonedDateTime.now(utc).minusHours(bootProperty.getRecordQueryInterval());
        LocalDateTime shutdownBefore = LocalDateTime.now().minusMinutes(bootProperty.getShutdownOver());
        String startTime = startDateTime.format(ApiConst.DATE_TIME_FORMATTER);

        String url = workspaceUrlPrefix + ApiConst.LIST_LOGIN_RECORD;
        url = url.replace("{startTime}", startTime);
        url = url.replace("{computerName}", computerName);

        String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, bootProperty.getHttpConnectionTimeout()).method(HttpMethods.GET));
        MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
        //判断状态码 OK(200)
        Integer statusCode = myHttpResponse.getStatusCode();
        Preconditions.checkState(HttpStatus.SC_OK == statusCode, "登陆记录获取失败，返回状态码" + statusCode);

        //body反序列化为桌面列表
        ObjectMapper mapper = new ObjectMapper();
        RecordRootBean recordRootBean = mapper.readValue(myHttpResponse.getBody(), RecordRootBean.class);
        for (Record record : recordRootBean.getRecords()) {
            String endTime = record.getConnection_end_time();
            if (null == endTime) {
                continue;
            }
            ZonedDateTime utcEndTime = ZonedDateTime.parse(endTime, ApiConst.NANO_DATE_TIME_FORMATTER.withZone(utc));
            LocalDateTime localEndTime = utcEndTime.withZoneSameInstant(local).toLocalDateTime();
            if (localEndTime.isAfter(shutdownBefore)) {
                log.info("****{}存在小于{}分钟记录 不关机", computerName, ApiConst.SHUTDOWN_OVER_MINUTE);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void shutDown(String desktopId) throws HttpProcessException {
        String url = workspaceUrlPrefix + ApiConst.SHUTDOW_DESKTOP;
        url = url.replace("{desktopId}", desktopId);

        String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, RequestConst.shutdownJson, token, 5, null, bootProperty.getHttpConnectionTimeout()).method(HttpMethods.POST));
        MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
        //判断状态码 ACCEPTED(202)
        Integer statusCode = myHttpResponse.getStatusCode();
        Preconditions.checkState(HttpStatus.SC_ACCEPTED == statusCode, "关机失败，返回状态码" + statusCode);


    }


}
