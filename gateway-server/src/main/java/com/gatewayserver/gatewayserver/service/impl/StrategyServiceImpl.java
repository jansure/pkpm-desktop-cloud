package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.strategy.Policies;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmTokenDAO;
import com.gatewayserver.gatewayserver.service.StrategyService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpHeader;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class StrategyServiceImpl implements StrategyService {
    @Resource
    private PkpmTokenDAO pkpmTokenDAO;
    @Resource
    private PkpmJobStatusDAO pkpmJobStatusDAO;
    @Resource
    private PkpmProjectDefDAO pkpmProjectDefDAO;
    @Resource
    private CommonRequestBeanBuilder commonRequestBeanBuilder;

    @SuppressWarnings("unused")
    public CommonRequestBean queryStrategy(CommonRequestBean commonRequestBean) {
        Integer statusCode = null;
        String message = null;
        String jsonStr = "";
        
        try {
	        CommonRequestBean requestBean = commonRequestBeanBuilder.buildBeanForQueryStrategy(commonRequestBean);
	        //todo  requestBean校验
	        String token = requestBean.getPkpmToken().getToken();
	        String url = requestBean.getPkpmWorkspaceUrl().getUrl()
					                .replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
					                .replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName());
    
            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            String body = myHttpResponse.getBody();

            if (HttpStatus.OK.value() == statusCode) {
                CommonRequestBean respRequestBean = JsonUtil.deserialize(body, CommonRequestBean.class);
                return respRequestBean;
            }
        } catch (HttpProcessException httpProcessException) {
            log.error(httpProcessException.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        throw Exceptions.newBusinessException("查询策略失败!");
    }


    @SuppressWarnings("unused")
    @Override
    public Integer updateStrategy(CommonRequestBean commonRequestBean) {
        Integer statusCode = null;
        String message = null;
        String jsonStr = "";

        Policies policies = commonRequestBean.getPolicies();
        Map<String, Object> policeMap = new HashMap<String, Object>();
        policeMap.put("policies", policies);

        CommonRequestBean requestBean = commonRequestBeanBuilder.buildUpdateStrategy(commonRequestBean.getProjectId());
        String token = requestBean.getPkpmToken().getToken();
        String url = requestBean.getPkpmWorkspaceUrl().getUrl()
				                .replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
				                .replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName());
        String jsonPolicies = JsonUtil.serialize(policeMap);
        System.out.println(jsonPolicies);
        try {
        	
            Header[] headers = HttpHeader.custom().contentType("application/json").other("X-Auth-Token", token).build();
            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, jsonPolicies, token, 5, "utf-8", 10000);
            String strMyHttpResponse = HttpClientUtil.mysend(config.method(HttpMethods.PUT));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strMyHttpResponse, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            if (HttpStatus.NO_CONTENT.value() == statusCode) {
                return statusCode;
            }
        } catch (HttpProcessException e) {
        	log.error(e.getMessage());
        } catch (Exception e) {
        	log.error(e.getMessage());
        }
        throw Exceptions.newBusinessException("修改策略失败,请重新修改");
    }
}
