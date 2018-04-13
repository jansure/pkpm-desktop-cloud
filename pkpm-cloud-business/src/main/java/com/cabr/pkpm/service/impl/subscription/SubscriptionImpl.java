package com.cabr.pkpm.service.impl.subscription;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cabr.pkpm.entity.product.ComponentVO;
import com.cabr.pkpm.entity.subscription.SubsCription;
import com.cabr.pkpm.entity.subsdetails.SubsDetails;
import com.cabr.pkpm.entity.subsdetails.SubsDetailsVO;
import com.cabr.pkpm.entity.user.UserInfo;
import com.cabr.pkpm.entity.workorder.ProductNameVO;
import com.cabr.pkpm.entity.workorder.WorkOrder;
import com.cabr.pkpm.entity.workorder.WorkOrderVO;
import com.cabr.pkpm.mapper.subscription.SubsCriptionMapper;
import com.cabr.pkpm.mapper.subsdetails.SubsDetailsMapper;
import com.cabr.pkpm.mapper.workorder.WorkOrderMapper;
import com.cabr.pkpm.service.product.IProductService;
import com.cabr.pkpm.service.subscription.ISubscription;
import com.cabr.pkpm.utils.IDUtil;
import com.cabr.pkpm.utils.ResponseResult;
import com.cabr.pkpm.utils.StringUtil;
import com.cabr.pkpm.utils.sdk.ClientDemo;
import com.cabr.pkpm.utils.sdk.RedisCacheUtil;
import com.cabr.pkpm.vo.MyProduct;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
@Service
@Transactional
public class SubscriptionImpl implements ISubscription {
	
	@Autowired
	private SubsCriptionMapper subscriptionMapper;
	@Autowired
	private SubsDetailsMapper subsDetailsMapper;
	@Autowired
	private IProductService productService;
	@Autowired
	private WorkOrderMapper workOrderMapper;
	@Autowired
	private RedisCacheUtil<MyProduct> redisCacheUtil;
	
	protected ResponseResult result = new ResponseResult();
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 保存订单，保存工单，保存订单明细
	 * 
	 */
	@SuppressWarnings("unused")
	@Override
	public ResponseResult saveSubsDetails(UserInfo userInfo,WorkOrderVO wo) {
		
		SubsCription subscription = new SubsCription();
		long subsId = IDUtil.genOrderId();
		subscription.setSubsId(subsId);
		LocalDateTime date = LocalDateTime.now();
		subscription.setCreateTime(date);
		subscription.setPayChannel("0");
		Integer userId = userInfo.getUserID();
		subscription.setUserId(userId);
		
		List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
		
		Integer subscriptionCount = subscriptionMapper.saveSubscription(subscription);
		if(subscriptionCount<1){
			this.result.set("创建订单失败,请您重试", 0);
			return this.result;
		}
		SubsDetails subsDetails = new SubsDetails();
		subsDetails.setSubsId(subsId);
		Integer productId = (Integer) wo.getProductId();
		subsDetails.setProductId(productId);
		subsDetails.setCreateTime(date);
		subsDetails.setCloudStorageTimeId(wo.getCloudStorageTimeId());
		subsDetails.setCloudStorageTime(Integer.parseInt(StringUtil.substr(wo.getCloudStorageTimeName())));
		
		Integer subsDetailsCount = subsDetailsMapper.saveSubsDetails(subsDetails);
		if(subsDetailsCount<1){
			this.result.set("创建订单失败,请您重试", 0);
			return this.result;
		}
		
//		List<ProductNameVO> productNameVOList = wo.getProductNameVOList();
//		StringBuilder stringBuilder = new StringBuilder();
//		for (ProductNameVO productNameVO : productNameVOList) {
//			WorkOrder workOrder = new WorkOrder();
//			workOrder.setCreateTime(date);
//			workOrder.setWorkId(subsId);
//			workOrder.setProductId(wo.getProductId());
//			workOrder.setProductName(wo.getProductName());
//			workOrder.setUserId(userInfo.getUserID());
//			workOrder.setUserName(userInfo.getUserName());
//			workOrder.setUserLoginPassWord(userInfo.getUserLoginPassword());
//			workOrder.setUserMobileNumber(userInfo.getUserMobileNumber());
//			workOrder.setRegionId(wo.getRegionId());
//			workOrder.setRegionName(wo.getRegionName());
//			workOrder.setComponentId(Integer.parseInt(productNameVO.getProductId()));
//			workOrder.setComponentName(productNameVO.getProductName());
//			workOrder.setHostConfigId(wo.getHostConfigId());
//			workOrder.setHostConfigName(wo.getHostConfigName());
//			workOrder.setCloudStorageTimeId(wo.getCloudStorageTimeId());
//			workOrder.setCloudStorageTimeName(wo.getCloudStorageTimeName());
//			workOrderList.add(workOrder);
//			stringBuilder.append(productNameVO.getProductName()).append(",");
//		}
//		Integer workOrderCount = workOrderMapper.saveWorkerOrder(workOrderList);
//		if(workOrderCount<1){
//			this.result.set("创建订单失败,请您重试", 0);
//			return this.result;
//		}
		
		//1、应该是传入regionCode,获取到adId和projectId
		String regionName = wo.getRegionName();
		String areaCode = "cn-north-1";
		
		
		
		
		//2、传入commonrequestbean,创建ad和desktop
		CommonRequestBean commonRequestBean = new CommonRequestBean();
		
		
		//3、创建成功后,返回参数给前台
		
		
		
		
		
		this.result.set("申请立即使用成功,稍后为您立即开通", 1);
		
		redisCacheUtil.delete("MyProduct:"+userId);
		
//		String productNameStr= stringBuilder.toString();
//		productNameStr = productNameStr.substring(0,productNameStr.length()-1);
//		try {
//			String message = "用户名为:" + userInfo.getUserName() + "的客户正在申请"+productNameStr+"云产品服务,请您立即为其开通!";
//			String operationMobileNumber = "15117963494";
//			ClientDemo clientDemo = new ClientDemo();
//			clientDemo.smsPublish(operationMobileNumber, message);
//			this.result.set("发送短信成功", 1);
//			logger.debug(this.result.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("发送短信产异常:" + e);
//			this.result.set("发送短信失败", 0);
//		}
		
		
		return this.result;
		
	}

	
}
