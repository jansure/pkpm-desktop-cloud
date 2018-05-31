package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.desktop.constant.SubscriptionConstants;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.Desktop;
import com.gateway.common.dto.DesktopRequest;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.desktop.constant.SubscriptionStatusEnum;
import com.desktop.utils.DateUtils;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.PkpmProjectDef;
import com.github.pagehelper.PageHelper;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubscriptionDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubsDetails;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubsDetailsService;
import com.pkpmdesktopcloud.redis.RedisCache;

@Service
public class PkpmCloudSubsDetailsServiceImpl implements PkpmCloudSubsDetailsService {
	
	@Resource
	private PkpmCloudSubscriptionDAO subscriptionMapper;
	
	@Resource
	private PkpmCloudSubsDetailsDAO subsDetailsMapper;
	
	@Resource
	private PkpmCloudProductDefDAO productMapper;
	
	@Resource
	private PkpmCloudComponentDefDAO componentMapper;
	
	@Value("${server.host}")
	private String serverHost;
	
	@Override
	public PageBean<MyProduct> showList(int userId,Integer currentPage,Integer pageSize) {
		
		String destinationIp = null;
		
		PageHelper.startPage(currentPage, pageSize);
		
		//RedisCache cache = new RedisCache(MY_PRODUCT_ID);
		//List<MyProduct> myProducts = (List<MyProduct>)cache.getObject(userId);
		List<MyProduct> myProducts = new ArrayList<>();
		LocalDateTime nowTime = LocalDateTime.now();

		// 若存在Redis缓存，从缓存中读取
		/*if(myProducts!= null && myProducts.size()>0) {
			
			for (MyProduct myProduct : myProducts) {
				String invalid = myProduct.getInvalidTime();
				LocalDateTime invalidTime = StringOrDate.stringToDate(invalid, "yyyy年MM月dd日  HH:mm:ss");
				boolean flagTime;
				if (nowTime.isAfter(invalidTime)) {
					flagTime = false;
				} else {
					flagTime = true;
				}
				myProduct.setFlagTime(flagTime);
			}
			this.result.set("读取成功", 1, myProducts.size()+"", myProducts);
			return this.result;
		} else {*/
			List<PkpmCloudSubscription> subsCriptionList = subscriptionMapper.findSubsCriptionByUserId(userId);
			
			for (PkpmCloudSubscription subsCription : subsCriptionList) {
				
				Long subsId = subsCription.getSubsId();
				List<PkpmCloudSubsDetails> subsDetails = subsDetailsMapper.findSubsDetailsList(subsId);
				
				for (PkpmCloudSubsDetails subs : subsDetails) {

					Integer productId = subs.getProductId();



					LocalDateTime createTime = subs.getCreateTime();
					String create = DateUtils.time2String(createTime, "yyyy年MM月dd日  HH:mm:ss");
					LocalDateTime invalidTime = subs.getInvalidTime();
					boolean flagTime;
					
					//判断是否过期
					if (nowTime.isAfter(invalidTime)) {
						flagTime = false;
					} else {
						flagTime = true;
					}
					
					String invalid = DateUtils.time2String(invalidTime, "yyyy年MM月dd日  HH:mm:ss");
					List<PkpmCloudProductDef> products = productMapper.getProductByProductId(productId);
					String productDesc = products.get(0).getProductDesc(); 
					List<String> componentNames = new ArrayList<>();
					
					for (PkpmCloudProductDef productInfo : products) {
						
						Integer componentId = productInfo.getComponentId();
						Integer componentType = 0;
						String componentName = componentMapper.getComponentName(componentId, componentType);
						
						if (StringUtils.isEmpty(componentName)) {
							continue;
						}
						componentNames.add(componentName);
					}





					String desktopId = subsCription.getDesktopId();

					MyProduct myProduct = new MyProduct();


					//根据productId查询component_def中配置信息
					for (PkpmCloudProductDef productInfo : products) {

						Integer componentId = productInfo.getComponentId();
						Integer componentType = 1;
						PkpmCloudComponentDef pkpmCloudComponentDef = componentMapper.getComponentInfo(componentId, componentType);

						if (pkpmCloudComponentDef == null ) {
							continue;
						}
						String hostConfigName = pkpmCloudComponentDef.getComponentName();
						myProduct.setHostConfigName(hostConfigName);
					}

					myProduct.setComponentName(componentNames);
					myProduct.setCreateTime(create);
					myProduct.setInvalidTime(invalid);
					myProduct.setProductDesc(productDesc);
					myProduct.setFlagTime(flagTime);
					
					String status2 = subsCription.getStatus();
					String areaCode = subsCription.getAreaCode();
					
					//add projectid、subsid、adid、areacode
					String projectId = subsCription.getProjectId();
					
					myProduct.setSubsId(subsId);
					myProduct.setAdId(subsCription.getAdId());
					myProduct.setProjectId(projectId);
					myProduct.setAreaCode(areaCode);


					// 根据desktopId  查询计算机名字
//					if(StringUtils.isNotEmpty(desktopId)){
//						
//						myProduct.setDesktopId(desktopId);
//						String urlGetComputerName = serverHost + "/operator/queryComputerName";
//						CommonRequestBean commonRequestBean = new CommonRequestBean();
//						commonRequestBean.setDesktopId(desktopId);
//						String jsonCommonRequestBean = JsonUtil.serializeEx(commonRequestBean);
//						try {
//							String computerNameResponse = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(
//									urlGetComputerName,jsonCommonRequestBean,5,"utf-8", 10000).method(HttpMethods.POST));
//							MyHttpResponse computerNameHttpResponse = JsonUtil.deserialize(computerNameResponse, MyHttpResponse.class);
//							ResultObject result = getResultObject(computerNameHttpResponse);
//							String computerName = (String) result.getData();
//							if(StringUtils.isEmpty(computerName)){
//								myProduct.setComputerName(computerName);
//							}
//
//							//根据projectId和desktopId 查询 桌面运行状态
//							String urlDesktopStatus = serverHost + "/desktop/queryDesktopDetail";
//							commonRequestBean.setProjectId(projectId);
//							String desktopStatusResponse = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(
//									urlDesktopStatus,jsonCommonRequestBean,5,"utf-8", 10000).method(HttpMethods.POST));
//							MyHttpResponse  desktopStatusHttpResponse = JsonUtil.deserialize(computerNameResponse, MyHttpResponse.class);
//							ResultObject desktopStatusResult = getResultObject(desktopStatusHttpResponse);
//							String  desktopStatusData = (String) desktopStatusResult.getData();
//							DesktopRequest desktopRequest = JsonUtil.deserialize(desktopStatusData, DesktopRequest.class);
//							List<Desktop> desktops = desktopRequest.getDesktops();
//							Desktop desktop = desktops.get(0);
//							String desktopStatus = desktop.getStatus();
//							myProduct.setDesktopStatus(desktopStatus);
//
//						} catch (HttpProcessException e) {
//							e.printStackTrace();
//						}
//					}
					//根据projectId和areacode 查询 destinationIp
					try {

						String urlGetProjectDef =serverHost + "/params/getProjectDef?areaCode=" + areaCode + "&projectId=" +  projectId;
						String adAndProjectResponse = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(urlGetProjectDef,  5, "utf-8", 100000).method(HttpMethods.GET));
						  MyHttpResponse adAndProjectHttpResponse = JsonUtil.deserialize(adAndProjectResponse, MyHttpResponse.class);
						ResultObject result = getResultObject(adAndProjectHttpResponse);
						String str = (String) result.getData();
						PkpmProjectDef pkpmProjectDef = JsonUtil.deserialize(str, PkpmProjectDef.class);
						destinationIp = pkpmProjectDef.getDestinationIp();
						
					} catch (HttpProcessException e) {
						
						e.printStackTrace();
					}


					if(StringUtils.isNotEmpty(status2) && SubscriptionStatusEnum.VALID.toString().equals(status2)){
						myProduct.setStatus(SubscriptionConstants.SUCCESS_STATUS);
						myProduct.setHostIp(destinationIp);
					}else if (StringUtils.isNotEmpty(status2) && SubscriptionStatusEnum.FAILED.toString().equals(status2)){
						myProduct.setStatus(SubscriptionConstants.FAILD_STATUS);
					}else{
						myProduct.setStatus(SubscriptionConstants.PROCESS_STATUS);
					}
					
					myProducts.add(myProduct);
				}
				
			//}
			
			//cache.putObject(userId, myProducts);
		}
			
		PageBean<MyProduct> pageData = new PageBean<>(currentPage,pageSize,myProducts.size());
		pageData.setItems(myProducts);
		
		return pageData;
	}

	private ResultObject getResultObject(MyHttpResponse myHttpResponse) {
		Integer adStatusCode = myHttpResponse.getStatusCode();
		if(HttpStatus.OK.value() != adStatusCode){
            throw  Exceptions.newBusinessException(myHttpResponse.getMessage());
        }
		String body = myHttpResponse.getBody();
		ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
		Integer code = result.getCode();
		if(HttpStatus.OK.value() != code){
            throw  Exceptions.newBusinessException(result.getMessage());
        }
		return result;
	}

}
