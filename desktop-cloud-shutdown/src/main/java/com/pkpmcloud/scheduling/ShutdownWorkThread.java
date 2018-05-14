package com.pkpmcloud.scheduling;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.exception.Exceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.common.util.JsonUtil;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmcloud.constants.ApiConst;
import com.pkpmcloud.constants.RequestConst;
import com.pkpmcloud.model.Desktop;
import com.pkpmcloud.model.Record;
import com.pkpmcloud.model.RecordRootBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Slf4j
public class ShutdownWorkThread implements Runnable {



    private String token;
    private String workspaceUrlPrefix;
    private Set<String> whitelist;
    private List<Desktop> desktops;

    public ShutdownWorkThread(String token, String workspaceUrlPrefix, Set<String> whitelist, List<Desktop> desktops) {
        this.token = token;
        this.workspaceUrlPrefix = workspaceUrlPrefix;
        this.whitelist = whitelist;
        this.desktops = desktops;
    }

    @Override
    public void run() {
        log.info("###>{}线程启动 处理{}个请求",Thread.currentThread().getName(),desktops.size());
        int order=0;
        for (Desktop desktop : desktops) {
            String desktopId = desktop.getDesktop_id();
            String computerName = desktop.getComputer_name();
            String loginStatus = desktop.getLogin_status();

            Boolean ifShutdown = (!ApiConst.CONNECTED_STATUS.equals(loginStatus))
                    &&(!whitelist.contains(computerName));
            System.out.println(ifShutdown);
            Boolean lessThan =!findLoginRecordLessThan(computerName);
            System.out.println("LessThan"+lessThan);
            ifShutdown=ifShutdown&&lessThan;
            if (ifShutdown) {
                log.info("###-{}-发起关机请求:{}", ++order,computerName);
                /*shutDown(desktopId);*/
            }
        }
    }


    private Boolean findLoginRecordLessThan(String computerName) {
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(8L).minusHours(ApiConst.QUERY_BEFORE_HOUR);
        LocalDateTime shutdownBefore = LocalDateTime.now().minusMinutes(ApiConst.SHUTDOWN_OVER_MINUTE);
        String startTime = startDateTime.format(ApiConst.DATE_TIME_FORMATTER);

        String url = workspaceUrlPrefix + ApiConst.LIST_LOGIN_RECORD;
        url = url.replace("{startTime}", startTime);
        url = url.replace("{computerName}", computerName);
        try {
            log.info("发起请求{}",url);
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, 10000).method(HttpMethods.GET));
            log.info("收到反馈{}",response);
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 OK(200)
            Integer statusCode = myHttpResponse.getStatusCode();
            log.info(statusCode+"StatusCode");
            Preconditions.checkState(HttpStatus.SC_OK == statusCode, "登陆记录获取失败，返回状态码" + statusCode);

            //body反序列化为桌面列表
            ObjectMapper mapper = new ObjectMapper();
            RecordRootBean recordRootBean = mapper.readValue(myHttpResponse.getBody(), RecordRootBean.class);
            log.info(recordRootBean.getRecords()+"");
            for (Record record : recordRootBean.getRecords()) {
                LocalDateTime endTime = LocalDateTime.parse(record.getConnection_end_time(),ApiConst.NANO_DATE_TIME_FORMATTER);
                if (null == endTime) {
                    continue;
                }
                log.info(endTime+"");
                if (endTime.isAfter(shutdownBefore)) {
                    log.info("****{}存在小于{}分钟记录 不关机", computerName, ApiConst.SHUTDOWN_OVER_MINUTE);
                    return Boolean.TRUE;
                }
            }
            log.info("return false");
            return Boolean.FALSE;
        } catch (HttpProcessException | IOException e) {
            e.printStackTrace();
        }
        log.info("错误");
        return null;
    }

    private void shutDown(String desktopId) {
        String url = workspaceUrlPrefix + ApiConst.SHUTDOW_DESKTOP;
        url = url.replace("{desktopId}", desktopId);
        try {
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, RequestConst.shutdownJson, token, 5, null, 10000).method(HttpMethods.POST));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 ACCEPTED(202)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_ACCEPTED == statusCode, "关机失败，返回状态码" + statusCode);

        } catch (HttpProcessException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        }
    }



}
