package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmTokenDAO;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.Desktop;
import com.gatewayserver.gatewayserver.dto.record.Record;
import com.gatewayserver.gatewayserver.dto.record.Records;
import com.gatewayserver.gatewayserver.dto.resp.userlist.UserList;
import com.gatewayserver.gatewayserver.service.UserService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private PkpmTokenDAO pkpmTokenDAO;
    @Resource
    private PkpmJobStatusDAO pkpmJobStatusDAO;
    @Resource
    private PkpmProjectDefDAO pkpmProjectDefDAO;
    @Resource
    private CommonRequestBeanBuilder commonRequestBeanBuilder;

    @SuppressWarnings("unused")
    public UserList queryDesktopUserList(CommonRequestBean commonRequestBean) {

        Integer statusCode = null;
        String marker = null;
        String message = null;
        String jsonStr = "";
        try {
	        CommonRequestBean requestBean = commonRequestBeanBuilder.buildQueryDesktopUserList(commonRequestBean);
	        String token = requestBean.getPkpmToken().getToken();
	        String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					                .replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					                .replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName());
	
	        List<NameValuePair> params = new ArrayList<>();
	        String userName = commonRequestBean.getUserName();
	        String userEmail = commonRequestBean.getUserEmail();
	        String limit = commonRequestBean.getLimit();
	        List<Desktop> desktops = commonRequestBean.getDesktops();
	        if (desktops != null) {
	            Desktop desktop = desktops.get(0);
	            marker = desktop.getMarker();
	        }
	        if (StringUtils.isNotBlank(userName)) {
	            params.add(new BasicNameValuePair("user_name", userName));
	        }
	        if (StringUtils.isNotBlank(userEmail)) {
	            params.add(new BasicNameValuePair("user_email", userEmail));
	        }
	        if (StringUtils.isNotBlank(marker)) {
	            params.add(new BasicNameValuePair("marker", marker));
	        }
	        if (StringUtils.isNotBlank(limit)) {
	            params.add(new BasicNameValuePair("limit", limit));
	        }
	        if (params.size() > 0) {
	            try {
	                url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            if (HttpStatus.OK.value() == statusCode) {
                String body = myHttpResponse.getBody();
                UserList userList = JsonUtil.deserialize(body, UserList.class);
                return userList;
            }
        } catch (HttpProcessException e) {
            log.info(e.getMessage());
        } catch (Exception ee) {
           log.info(ee.getMessage());
        }
        throw Exceptions.newBusinessException("查询桌面用户列表失败,请从新查询");
    }

    @SuppressWarnings("unused")
    @Override
    public Records queryUserLoginRecord(CommonRequestBean commonRequestBean) {
        Integer statusCode = null;
        String message = null;
        String jsonStr = "";
        try {
	        CommonRequestBean requestBean = commonRequestBeanBuilder.buildQueryUserLoginRecord(commonRequestBean);
	        String token = requestBean.getPkpmToken().getToken();
	        String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					                .replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					                .replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName());
	
	        List<NameValuePair> params = new ArrayList<>();
	
	        String limit = commonRequestBean.getLimit();
	        params.add(new BasicNameValuePair("limit", limit));
	        Record record = commonRequestBean.getRecord();
	        if (record != null) {
	            String startTime = record.getStartTime();
	            String endTime = record.getEndTime();
	            String userName = record.getUserName();
	            String computerName = record.getComputerName();
	            String terminalType = record.getTerminalType();
	            String offset = record.getOffset();
	            if (startTime != null) {
	                params.add(new BasicNameValuePair("start_time", startTime));
	            }
	            if (endTime != null) {
	                params.add(new BasicNameValuePair("end_time", endTime));
	            }
	            if (userName != null) {
	                params.add(new BasicNameValuePair("user_name", userName));
	            }
	            if (computerName != null) {
	                params.add(new BasicNameValuePair("computer_name", computerName));
	            }
	            if (terminalType != null) {
	                params.add(new BasicNameValuePair("terminal_type", terminalType));
	            }
	            if (offset != null) {
	                params.add(new BasicNameValuePair("offset", offset));
	            }
	        }
	        if (params.size() > 0) {
	            try {
	                url = url + "?" + EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();

            if (HttpStatus.OK.value() == statusCode) {
                String body = myHttpResponse.getBody();
                Records records = JsonUtil.deserialize(body, Records.class);
                return records;
            }
        } catch (HttpProcessException e) {
           log.info(e.getMessage());
        } catch (Exception ee) {
           log.info(ee.getMessage());
        }
        throw Exceptions.newBusinessException("查询用户登录记录失败,请从新查询");
    }

}
