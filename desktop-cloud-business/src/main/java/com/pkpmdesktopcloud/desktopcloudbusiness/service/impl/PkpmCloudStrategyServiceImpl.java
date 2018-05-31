package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.dto.strategy.Policies;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.PkpmCloudStrategyVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudStrategyService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PkpmCloudStrategyServiceImpl  implements PkpmCloudStrategyService{  
	
	@Value("${server.host}")
	private String serverHost;
	
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userName
	 * @param pageSize
	 * @param pageNo
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudStrategyService#getStrategyByQuery(java.lang.String, int, int)  
	 */  
	@Override
	public List<PkpmCloudStrategyVO> getStrategyByQuery(String userName, int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return null;
	}
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param desktopId
	 * @return  
	 * @see com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudStrategyService#getStrategyByDesktopId(java.lang.String)  
	 */  
	@Override
	public Policies getStrategyByDesktopId(String desktopIds) {
		
		String urlStrategy = serverHost + "/strategy/queryStrategy";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("projectId", desktopIds);
        
		try {
            String strJson = JsonUtil.serialize(paramMap);
            String strResponse = HttpClientUtil.mysend(
                    HttpConfigBuilder.buildHttpConfigNoToken(urlStrategy, strJson, 5, "utf-8", 10000).method(HttpMethods.POST));
           
            MyHttpResponse myHttpResponse = JsonUtil.deserialize(strResponse, MyHttpResponse.class);
            Integer statusCode = myHttpResponse.getStatusCode();

            if(HttpStatus.OK.value() == statusCode){
                String body = myHttpResponse.getBody();
                ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
                Integer code = result.getCode();
                if(HttpStatus.OK.value() == code){
                    return (Policies) result.getData();
                }
            }

        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        
        throw  Exceptions.newBusinessException("查询策略失败,请重新尝试!");
	}

    
}
