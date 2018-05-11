package com.pkpmcloud.service.impl;

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
import com.pkpmcloud.dao.WhiteListDAO;
import com.pkpmcloud.model.*;
import com.pkpmcloud.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuhe
 * @description
 * @date 2018/5/10
 */
@Service
@Slf4j
public class ApiServiceImpl implements ApiService {


    private String token;
    private String projectId;
    private String areaName;
    private String adminName;
    private String adminPassword;

    private String workspaceUrlPrefix;


    @Resource
    private WhiteListDAO whiteListDAO;

    public ApiServiceImpl() {
    }

    //强制project信息赋值
    private void init(Project project) {
        projectId = project.getProjectId();
        areaName = project.getAreaName();
        adminName = project.getAdminName();
        adminPassword = project.getAdminPassword();
        workspaceUrlPrefix = ApiConst.WORKSPACE_PREFIX;
        workspaceUrlPrefix = workspaceUrlPrefix.replace("{areaName}", areaName).replace("{projectId}", projectId);
        setToken();
        log.info("====>>项目启动：项目信息{}/{} {}/{}", adminName, project.getAreaDesc(), projectId, areaName);
    }

    @Override
    public void invokeDesktopShutdownShell(Project project) {
        init(project);
        List<Desktop> desktops = listActiveDesktop();
        Set<String> whiteList = whiteListDAO.listProjectWhiteList(projectId);
        Preconditions.checkNotNull(desktops);
        Preconditions.checkNotNull(whiteList);
        if (desktops.size() == 0) {
            log.info("无开机桌面");
            return;
        }
        log.info("当前状态：开机桌面{}台",desktops.size());
        int order=0;
        for (Desktop desktop : desktops) {
            String desktopId = desktop.getDesktop_id();
            String computerName = desktop.getComputer_name();
            String loginStatus = desktop.getLogin_status();

            Boolean ifShutdown = ApiConst.DISCONNECTED_STATUS.equals(loginStatus)
                    && !whiteList.contains(computerName)
                    && !findLoginRecordLessThan(computerName);

            if (ifShutdown) {
                log.info("###-{}-发起关机请求:{}", ++order,computerName);
                shutDown(desktopId);
            }
        }
    }


    private String setToken() {
        //token置为空
        token = null;
        //拼装url及请求体
        String url = ApiConst.TOKEN.replace("{areaName}", areaName);
        String authJson = RequestConst.authJson
                .replaceAll("\\{adminName\\}", adminName)
                .replaceAll("\\{adminPassword\\}", adminPassword)
                .replaceAll("\\{projectId\\}", projectId);
        //发送请求 获取Token;
        try {
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, authJson, token, 5, null, 10000).method(HttpMethods.POST));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 Created(201)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_CREATED == statusCode, "token请求失败，返回状态码" + statusCode);
            //获取Token，写入成员变量token，用于其他函数使用
            Map<String, Object> respHeaders = myHttpResponse.getHeaders();
            String respToken = respHeaders.get("X-Subject-Token").toString();
            Preconditions.checkNotNull(respToken, "token值为空");
            token = respToken;
        } catch (HttpProcessException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        }
        return token;
    }


    private List<Desktop> listActiveDesktop() {
        String url = workspaceUrlPrefix + ApiConst.LIST_ACTIVE_DESKTOP;
        
        try {
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, 10000).method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 OK(200)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_OK == statusCode, "开机桌面列表请求失败，返回状态码" + statusCode);

            //body反序列化为桌面列表
            ObjectMapper mapper = new ObjectMapper();
            DesktopRootBean rootBean = mapper.readValue(myHttpResponse.getBody(), DesktopRootBean.class);
            return rootBean.getDesktops();
        } catch (HttpProcessException | IOException e) {
            throw Exceptions.newBusinessException(e.getMessage());
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
            String response = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfig(url, "", token, 5, null, 10000).method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(response, MyHttpResponse.class);
            //判断状态码 OK(200)
            Integer statusCode = myHttpResponse.getStatusCode();
            Preconditions.checkState(HttpStatus.SC_OK == statusCode, "登陆记录获取失败，返回状态码" + statusCode);

            //body反序列化为桌面列表
            ObjectMapper mapper = new ObjectMapper();
            RecordRootBean recordRootBean = mapper.readValue(myHttpResponse.getBody(), RecordRootBean.class);
            for (Record record : recordRootBean.getRecords()) {
                if (null == record.getConnection_end_time()) {
                    continue;
                }
                if (record.getConnection_end_time().isBefore(shutdownBefore)) {
                    log.info("****{}存在小于{}分钟记录 不关机", computerName, ApiConst.SHUTDOWN_OVER_MINUTE);
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (HttpProcessException | IOException e) {
            throw Exceptions.newBusinessException(e.getMessage());
        }
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
