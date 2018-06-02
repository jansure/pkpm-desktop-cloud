package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desktop.constant.ComponentTypeConstant;
import com.desktop.constant.DesktopConstant;
import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.IDUtil;
import com.desktop.utils.JsonUtil;
import com.desktop.utils.StringUtil;
import com.desktop.utils.exception.Exceptions;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.google.common.base.Preconditions;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;
import com.pkpm.httpclientutil.exception.HttpProcessException;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudComponentDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudProductDefDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.PkpmCloudSubscriptionDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudComponentDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudProductDef;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubsDetails;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.WorkOrderVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubscriptionService;
import com.pkpmdesktopcloud.redis.RedisCache;

@Service
@Transactional
public class PkpmCloudSubscriptionServiceImpl implements PkpmCloudSubscriptionService {
	
	private static final String SUBSCRIPTION_USERID_ID = "subsCriptionByUserId";
	
	@Resource
	private PkpmCloudSubscriptionDAO subscriptionMapper;
	
	@Resource
	private PkpmCloudSubsDetailsDAO subsDetailsMapper;
	
	@Resource
	private PkpmCloudProductDefDAO productMapper;
	
	@Resource
	private PkpmCloudComponentDefDAO componentMapper;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("${server.host}")
	private String serverHost;
	
	@Value("${userEmail}")
	private String userEmail;
	
	@Value("${invalidStatus}")
	private String invalidStatus;
	@Value("${dataVolumeSize}")
	private Integer dataVolumeSize;
	
	
	/**
	 * 1、保存订单，保存订单明细
	 * 2、获取projectId和AdId
	 * 3、调用创建桌面接口,返回给前台
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public PkpmOperatorStatus saveSubsDetails(PkpmCloudUserInfo userInfo,WorkOrderVO wo) {
		
		
		String adId = "";
		String projectId = "";
	try {
		//a、保存订单之前先查询有没有 初始化的订单
		Integer userId = userInfo.getUserId();
		Integer invalidCount = subscriptionMapper.selectCount(userId,invalidStatus);
		//测试阶段先注掉
		/*if(invalidCount >= 1){
			throw  Exceptions.newBusinessException("您有正在创建中的桌面,请重新尝试!");
		}*/
		Integer regionId = wo.getRegionId();
		PkpmCloudComponentDef regionComponentInfo = componentMapper.getComponentInfo(regionId, ComponentTypeConstant.region_type);
		String areaCode = regionComponentInfo.getComponentDesc();
		
	   //	String areaCode = "cn-north-1";
		String urlGetAdAndProject =serverHost + "/params/getAdAndProject?areaCode=" + areaCode ;
		String adAndProjectResponse = HttpClientUtil.mysend(HttpConfigBuilder.buildHttpConfigNoToken(urlGetAdAndProject,  5, "utf-8", 100000).method(HttpMethods.GET));
		
		MyHttpResponse adAndProjectHttpResponse = JsonUtil.deserialize(adAndProjectResponse, MyHttpResponse.class);
		Integer adStatusCode = adAndProjectHttpResponse.getStatusCode();
		
//		if( HttpStatus.OK.value() == adStatusCode){
//		
//			String body = adAndProjectHttpResponse.getBody();
//			ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
//			Integer code = result.getCode();
//			if(HttpStatus.OK.value() == code){
//				Map<String,String> map = (Map<String, String>) result.getData();
//				 adId = map.get("adId");
//				 projectId = map.get("projectId");
//			}
//		}
		if(HttpStatus.OK.value() != adStatusCode){
			throw  Exceptions.newBusinessException(adAndProjectHttpResponse.getMessage());
		}
		String body = adAndProjectHttpResponse.getBody();
		ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
		Integer code = result.getCode();
		if(HttpStatus.OK.value() != code){
			throw  Exceptions.newBusinessException(result.getMessage());
		}
		Map<String,String> map = (Map<String, String>) result.getData();
		adId = map.get("adId");
		projectId = map.get("projectId");
		
		PkpmCloudSubscription subscription = new PkpmCloudSubscription();
		long subsId = IDUtil.genOrderId();
		subscription.setSubsId(subsId);
		LocalDateTime date = LocalDateTime.now();
		subscription.setCreateTime(date);
		subscription.setPayChannel("0");
		subscription.setUserId(userId);
		subscription.setProjectId(projectId);
		subscription.setAdId(Integer.parseInt(adId));
		subscription.setStatus(invalidStatus);
		subscription.setAreaCode(areaCode);

		Integer subscriptionCount = subscriptionMapper.saveSubscription(subscription);
		if(subscriptionCount<1){
			throw  Exceptions.newBusinessException("保存订单失败,请您重试!");
		}
		
		PkpmCloudSubsDetails subsDetails = new PkpmCloudSubsDetails();
		subsDetails.setSubsId(subsId);
		Integer productId = (Integer) wo.getProductId();
		subsDetails.setProductId(productId);
		subsDetails.setCreateTime(date);
		subsDetails.setValidTime(date);

//		subsDetails.setCloudStorageTimeId(wo.getCloudStorageTimeId());
//		String cloudStorageName = componentMapper.getComponentName(wo.getCloudStorageTimeId(), ComponentTypeConstant.cloud_storage);
//		subsDetails.setCloudStorageTime(Integer.parseInt(StringUtil.substr(cloudStorageName)));

		//根据 云存储ID  查询  云存储时间
		Integer cloudStorageTimeId = wo.getCloudStorageTimeId();
		String cloudStorageName = componentMapper.getComponentName(wo.getCloudStorageTimeId(), ComponentTypeConstant.cloud_storage);
		String str = StringUtil.substr(cloudStorageName);
		long longStorageName = Long.parseLong(str);
		subsDetails.setInvalidTime(date.plusMonths(longStorageName));

		Integer subsDetailsCount = subsDetailsMapper.saveSubsDetails(subsDetails);
		if(subsDetailsCount<1){
			throw  Exceptions.newBusinessException("保存订单明细失败,请您重试!");
		}
		
		List<PkpmCloudProductDef> products = productMapper.getProductByProductId(productId);
		PkpmCloudProductDef productInfo = products.get(0);
		//String imageId = productInfo.getImageId();
		String productName = productInfo.getProductName();
		
		//2、传入commonrequestbean,创建ad和desktop
		CommonRequestBean commonRequestBean = new CommonRequestBean();
		commonRequestBean.setUserId(userId);
		commonRequestBean.setUserName(userInfo.getUserName());
		commonRequestBean.setUserLoginPassword(userInfo.getUserLoginPassword());
		commonRequestBean.setDataVolumeSize(dataVolumeSize);
		commonRequestBean.setSubsId(subsId);   
		commonRequestBean.setOperatorStatusId(null);  
		
		Integer hostConfigId = wo.getHostConfigId();
		PkpmCloudComponentDef hostConfigcomponentInfo = componentMapper.getComponentInfo(hostConfigId, ComponentTypeConstant.host_config);
		String hwProductId = hostConfigcomponentInfo.getHwProductId();
		commonRequestBean.setHwProductId(hwProductId);   // workspace.c2.large.windows
		
		//commonRequestBean.setOuName(ouName);
		//commonRequestBean.setOuName("远大北京公司/销售部");
		commonRequestBean.setUserEmail(userEmail);
		commonRequestBean.setProjectId(projectId);   
		commonRequestBean.setAdId(Integer.parseInt(adId));
		commonRequestBean.setImageId(StringUtils.isBlank(
				productInfo.getImageId())?
				DesktopConstant.DEFAULT_IMAGE_ID:productInfo.getImageId());   //997488ed-fa23-4671-b88c-d364c0405334

		//根据user_id和status查询计算机名

		String userName = userInfo.getUserName();
		//查询成功的条数
		Integer nextNum = 1 + subscriptionMapper.selectTotalById(userId);
		if(nextNum >= 1 + DesktopConstant.DESKTOP_OWN_MAX_ACCOUNT) {
			throw Exceptions.newBusinessException(
					String.format("您购买的桌面数量已经达到%d个上限，请重新注册账号进行购买!",
							DesktopConstant.DESKTOP_OWN_MAX_ACCOUNT));
		}


		/**
		 * @author xuhe and zhangshuai
		 * 获取计算机名
		*/
		//获取某个adid下状态不是FAILED的订单总数
		Integer countByAdId = subscriptionMapper.countByAdIdExceptFailedSubs(Integer.parseInt(adId));
		countByAdId++;
		//总长共计15位 如果位数超过15位，则自动缩减产品名 如PKPM-BIM-DESIGN100 缩减为PKPM-BIM-DES100(末尾IGN被删除)
		if(DesktopConstant.DESKTOP_NAME_MAX_LEN > productName.length()) {
			commonRequestBean.setGloryProductName(productName + countByAdId);
			if(productName.length() + countByAdId.toString().length() >
					DesktopConstant.DESKTOP_NAME_MAX_LEN) {
				Integer minus = countByAdId.toString().length() -
						(DesktopConstant.DESKTOP_NAME_MAX_LEN - productName.length());
				commonRequestBean.setGloryProductName(productName.substring(0,
						DesktopConstant.DESKTOP_NAME_MAX_LEN - minus
				) + countByAdId);
			}

		}
		else {
			commonRequestBean.setGloryProductName(productName.substring(0,
					DesktopConstant.DESKTOP_NAME_MAX_LEN - countByAdId.toString().length())
					+ countByAdId);
		}


		commonRequestBean.setAreaCode(areaCode);
		String urlCreateAdAndDesktop =serverHost + "/desktop/createAdAndDesktop";
		String strJson = JsonUtil.serialize(commonRequestBean);
		String strResponse = HttpClientUtil.mysend(
				HttpConfigBuilder.buildHttpConfigNoToken(urlCreateAdAndDesktop, strJson, 5, "utf-8", 10000).method(HttpMethods.POST));
		MyHttpResponse myHttpResponse = JsonUtil.deserialize(strResponse, MyHttpResponse.class);
		
		Integer statusCode = myHttpResponse.getStatusCode();
			//3、创建成功后,返回参数给前台
			if(HttpStatus.OK.value() == statusCode){
				
//				String body = myHttpResponse.getBody();
//				ResultObject result = JsonUtil.deserialize(body, ResultObject.class);
//				Integer code = result.getCode();
				 body = myHttpResponse.getBody();
				 result = JsonUtil.deserialize(body, ResultObject.class);
				 code = result.getCode();
				if(HttpStatus.OK.value() == code){
					
					PkpmOperatorStatus  pkpmOperatorStatus = new PkpmOperatorStatus();
					pkpmOperatorStatus.setProjectId(projectId);
					pkpmOperatorStatus.setSubsId(subsId);  //字段不统一
					pkpmOperatorStatus.setAdId(Integer.parseInt(adId));
					pkpmOperatorStatus.setUserId(userId);
					pkpmOperatorStatus.setAreaCode(areaCode);
					//redisCacheUtil.delete("MyProduct:"+userId);
					return pkpmOperatorStatus;
					
				}
			}
			
		} catch (HttpProcessException e) {
			e.printStackTrace();
	    }
		throw  Exceptions.newBusinessException("申请试用失败,请稍后重试!");
		
	}

	@Override
	public List<PkpmCloudSubscription> findSubsCriptionByUserId(Integer userId) {
		
		RedisCache cache = new RedisCache(SUBSCRIPTION_USERID_ID);
		List<PkpmCloudSubscription> subsCriptionList = (List<PkpmCloudSubscription>)cache.getObject(userId);
		
		// 若存在Redis缓存，从缓存中读取
		if(subsCriptionList != null) {
			
			return subsCriptionList;
		}
		
		// 若不存在对应的Redis缓存，从数据库查询
		subsCriptionList = subscriptionMapper.findSubsCriptionByUserId(userId);
		// 写入Redis缓存
		cache.putObject(userId, subsCriptionList);
		return subsCriptionList;
	}
	/**
	 *根据subsId更新订单状态
	 *
	 * @author xuhe
	 * @param subsCription
	 * @return java.lang.String
	 */
	@Override
	public String  updateSubsCriptionBySubsId(PkpmCloudSubscription subsCription) {

		int result =subscriptionMapper.updateSubsCriptionBySubsId(subsCription);
		Preconditions.checkArgument(result == 1,"订单更新失败");
		return "更新状态成功";
	}

}
