package com.gateway.cloud.business.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gateway.cloud.business.entity.ProductInfo;
import com.gateway.cloud.business.entity.SubsDetails;
import com.gateway.cloud.business.mapper.ComponentMapper;
import com.gateway.cloud.business.mapper.ProductMapper;
import com.gateway.cloud.business.mapper.SubsDetailsMapper;
import com.gateway.cloud.business.mapper.SubscriptionMapper;
import com.gateway.cloud.business.mapper.WorkOrderMapper;
import com.gateway.cloud.business.service.SubsDetailsService;
import com.gateway.cloud.business.utils.ResponseResult;
import com.gateway.cloud.business.utils.sdk.PageBean;
import com.gateway.cloud.business.utils.sdk.RedisCacheUtil;
import com.gateway.cloud.business.utils.sdk.StringOrDate;
import com.gateway.cloud.business.vo.MyProduct;
import com.github.pagehelper.PageHelper;

@Service
public class SubsDetailsServiceImpl implements SubsDetailsService {
	
	@Resource
	private SubscriptionMapper subscriptionMapper;
	
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

	@Override
	public ResponseResult showList(int userId,Integer currentPage,Integer pageSize) {
		
		PageHelper.startPage(currentPage, pageSize);
		
		List<MyProduct> myProducts = redisCacheUtil.getCacheList("MyProduct:"+userId);
		
		LocalDateTime nowTime = LocalDateTime.now();
		// 若存在Redis缓存，从缓存中读取
		if(myProducts!= null && myProducts.size()>0) {
			
			for (MyProduct myProduct : myProducts) {
				String invalid = myProduct.getInvalidtime();
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
		} else {
			List<Long> subsIds = subscriptionMapper.findSubsId(userId);
			
			for (Long subsId : subsIds) {
				
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
					List<String> hostIp = workOrderMapper.findHostIp(userId, subsId, productId);
					List<Integer> status = workOrderMapper.findStatus(userId, subsId, productId);
					
					if (null == hostIp || hostIp.size() == 0) {
						this.result.set("主机Ip不能为空", 0);
						return this.result;
					}
					
					if (null == status || status.size() == 0) {
						this.result.set("status不能为空", 0);
						return this.result;
					}
					
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
					myProduct.setInvalidtime(invalid);
					myProduct.setProductDesc(productDesc);
					myProduct.setFlagTime(flagTime);
					myProduct.setHostIp(hostIp.get(0));
					myProduct.setStatus(status.get(0));
					
					myProducts.add(myProduct);
				}
				
			}
			
			PageBean<MyProduct> pageData = new PageBean<>(currentPage,pageSize,myProducts.size());
			
			pageData.setItems(myProducts);
			
			redisCacheUtil.setCacheList("MyProduct:"+userId, pageData.getItems());
			this.result.set("读取成功", 1, pageData.getTotalNum()+"", myProducts);
		    return this.result;
		}
	}

}
