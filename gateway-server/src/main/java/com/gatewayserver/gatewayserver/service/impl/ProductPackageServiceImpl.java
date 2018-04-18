package com.gatewayserver.gatewayserver.service.impl;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.product.Products;
import com.gatewayserver.gatewayserver.dao.PkpmJobStatusDAO;
import com.gatewayserver.gatewayserver.dao.PkpmProjectDefDAO;
import com.gatewayserver.gatewayserver.dao.PkpmTokenDAO;
import com.gatewayserver.gatewayserver.service.ProductPackageService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
@Transactional
public class ProductPackageServiceImpl implements ProductPackageService {

    @Resource
    private PkpmTokenDAO pkpmTokenDAO;
    @Resource
    private PkpmJobStatusDAO pkpmJobStatusDAO;
    @Resource
    private PkpmProjectDefDAO pkpmProjectDefDAO;
    @Resource
    private CommonRequestBeanBuilder commonRequestBeanBuilder;

    @SuppressWarnings("unused")
	@Override
    public Products queryProductPackage(CommonRequestBean commonRequestBean) {
        Integer statusCode = null;
        String message = null;
        String jsonStr = "";

        CommonRequestBean requestBean = commonRequestBeanBuilder.buildQueryProductPackage(commonRequestBean);
        String token = requestBean.getPkpmToken().getToken();
        String url = requestBean.getPkpmWorkspaceUrl().getUrl()
				                .replaceAll("\\{projectId\\}", requestBean.getPkpmWorkspaceUrl().getProjectId())
				                .replaceAll("\\{areaName\\}", requestBean.getPkpmWorkspaceUrl().getAreaName());

        String availabilityZone = commonRequestBean.getAvailabilityZone();
        if (availabilityZone != null) {
            url = url + "?availability_zone=" + availabilityZone;
        }

        try {
        	
            HttpConfig config = HttpConfigBuilder.buildHttpConfig(url, token, 5, "utf-8", 10000);
            String responseStr = HttpClientUtil.mysend(config.method(HttpMethods.GET));
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(responseStr, MyHttpResponse.class);
            statusCode = myHttpResponse.getStatusCode();
            String body = myHttpResponse.getBody();

            if ( HttpStatus.OK.value() == statusCode) {
                Products products = JsonUtil.deserialize(body, Products.class);
                return products;
            }
        } catch (HttpProcessException httpProcessException) {
            log.error(httpProcessException.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        throw Exceptions.newBusinessException("查询产品套餐失败,请重新尝试！");
    }
}
