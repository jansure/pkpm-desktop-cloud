package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.DesktopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DesktopServiceImpl  implements DesktopService{

    @Value("${server.host}")
    private String serverHost;

    @Override
    public String operateDesktop(CommonRequestBean commonRequestBean) {

        String urlDesktopStatus =serverHost + "/desktop/operateDesktop";
        try {
            String strJson = JsonUtil.serialize(commonRequestBean);
            String strResponse = HttpClientUtil.mysend(
                    HttpConfigBuilder.buildHttpConfigNoToken(urlDesktopStatus, strJson, 5, "utf-8", 10000).method(HttpMethods.POST));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strResponse, MyHttpResponse.class);
            Integer statusCode = myHttpResponse.getStatusCode();

            if(HttpStatus.OK.value() == statusCode){
                String body = myHttpResponse.getBody();
                ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
                Integer code = result.getCode();
                if(HttpStatus.OK.value() == code){
                    return (String) result.getData();
                }
            }

        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        throw  Exceptions.newBusinessException("操作桌面失败,请重新尝试!");

    }
}
