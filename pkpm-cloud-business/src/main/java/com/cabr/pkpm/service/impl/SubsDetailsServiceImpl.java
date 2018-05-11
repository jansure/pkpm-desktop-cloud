package com.cabr.pkpm.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cabr.pkpm.entity.product.ProductInfo;
import com.cabr.pkpm.entity.Subscription.Subscription;
import com.cabr.pkpm.entity.subsdetails.SubsDetails;
import com.cabr.pkpm.mapper.component.ComponentMapper;
import com.cabr.pkpm.mapper.product.ProductMapper;
import com.cabr.pkpm.mapper.Subscription.SubscriptionMapper;
import com.cabr.pkpm.mapper.subsdetails.SubsDetailsMapper;
import com.cabr.pkpm.mapper.workorder.WorkOrderMapper;
import com.cabr.pkpm.service.ISubsDetailsService;
import com.cabr.pkpm.utils.ResponseResult;
import com.cabr.pkpm.utils.sdk.PageBean;
import com.cabr.pkpm.utils.sdk.RedisCacheUtil;
import com.cabr.pkpm.utils.sdk.StringOrDate;
import com.cabr.pkpm.vo.MyProduct;
import com.desktop.constant.SubscriptionStatusEnum;
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

@Service
public class SubsDetailsServiceImpl implements ISubsDetailsService {
	
	@Resource
	private SubscriptionMapper SubscriptionMapper;
	
	@Resource
	private SubsDetailsMapper subsDetailsMapper;
	
	@Resource
	private ProductMapper productMapper;
	
	@Resource
	private ComponentMapper componentMapper;
	
	@Resource
	private WorkOrderMapper workOrderMapper;
	
	@Resource
	private RedisCacheUtil<MyProduct> redisCacheUtil;

	protected ResponseResult result=new ResponseResult();  
	
	@Value("${server.host}")
	private String serverHost;

	@Override
	public ResponseResult showList(int userId,Integer currentPage,Integer pageSize) {
		
		String destinationIp = null;
		
		PageHelper.startPage(currentPage, pageSize);
		
		List<MyProduct> myProducts = redisCacheUtil.getCacheList("MyProduct:"+userId);
		
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
			
			List<Subscription> SubscriptionList = SubscriptionMapper.findSubscriptionByUserId(userId);
			
			for (Subscription Subscription : SubscriptionList) {
				
				Long subsId = Subscription.getSubsId();
				List<SubsDetails> subsDetails = subsDetailsMapper.findSubsDetailsList(subsId);
				
				for (SubsDetails subs : subsDetails) {
					Integer productId = subs.getProductId();
					LocalDateTime createTime = subs.getCreateTime();
					String create = StringOrDate.dateToString(createTime, "yyyy年MM月dd日  HH:mm:ss");
					LocalDateTime invalidTime = subs.getInvalidTime();
					boolean flagTime;
					
					//判断是否过期
					if (nowTime.isAfter(invalidTime)) {
						flagTime = false;
					} else {
						flagTime = true;
					}
					
					String invalid = StringOrDate.dateToString(invalidTime, "yyyy年MM月dd日  HH:mm:ss");
					List<ProductInfo> products = productMapper.getProductByProductId(productId);
					String productDesc = products.get(0).getProductDesc(); 
					List<String> componentNames = new ArrayList<>();
					
					for (ProductInfo productInfo : products) {
						
						Integer componentId = productInfo.getComponentId();
						Integer componentType = 0;
						String componentName = componentMapper.getComponentName(componentId, componentType);
						
						if (StringUtils.isEmpty(componentName)) {
							continue;
						}
						componentNames.add(componentName);
					}

					MyProduct myProduct = new MyProduct();
					
					myProduct.setComponentName(componentNames);
					myProduct.setCreateTime(create);
					myProduct.setInvalidTime(invalid);
					myProduct.setProductDesc(productDesc);
					myProduct.setFlagTime(flagTime);
					
					String status2 = Subscription.getStatus();
					String areaCode = Subscription.getAreaCode();
					
					//add projectid、subsid、adid、areacode
					String projectId = Subscription.getProjectId();
					
					myProduct.setSubsId(subsId);
					myProduct.setAdId(Subscription.getAdId());
					myProduct.setProjectId(projectId);
					myProduct.setAreaCode(areaCode);
					try {
						
						String urlGetProjectDef =serverHost + "/params/getProjectDef?areaCode=" + areaCode + "&projectId=" +  projectId;
						String adAndProjectResponse = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(urlGetProjectDef,  5, "utf-8", 100000).method(HttpMethods.GET));
						MyHttpResponse adAndProjectHttpResponse = JsonUtil.deserialize(adAndProjectResponse, MyHttpResponse.class);
						Integer adStatusCode = adAndProjectHttpResponse.getStatusCode();
						if(HttpStatus.OK.value() != adStatusCode){
							throw  Exceptions.newBusinessException(adAndProjectHttpResponse.getMessage());
						}
						String body = adAndProjectHttpResponse.getBody();
						ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
						Integer code = result.getCode();
						if(HttpStatus.OK.value() != code){
							throw  Exceptions.newBusinessException(result.getMessage());
						}
						String str = (String) result.getData();
						PkpmProjectDef pkpmProjectDef = JsonUtil.deserialize(str, PkpmProjectDef.class);
						destinationIp = pkpmProjectDef.getDestinationIp();
						
					} catch (HttpProcessException e) {
						
						e.printStackTrace();
					}
					if(StringUtils.isNotEmpty(status2) && SubscriptionStatusEnum.VALID.toString().equals(status2)){
						myProduct.setStatus(1);
						myProduct.setHostIp(destinationIp);
					}else{
						myProduct.setStatus(0);
					}
					
					myProducts.add(myProduct);
				}
				
			}
	
			
			PageBean<MyProduct> pageData = new PageBean<>(currentPage,pageSize,myProducts.size());
			
			pageData.setItems(myProducts);
			
			//redisCacheUtil.setCacheList("MyProduct:"+userId, pageData.getItems());
			this.result.set("读取成功", 1, pageData.getTotalNum()+"", myProducts);
		    return this.result;
		//}
	}

}
