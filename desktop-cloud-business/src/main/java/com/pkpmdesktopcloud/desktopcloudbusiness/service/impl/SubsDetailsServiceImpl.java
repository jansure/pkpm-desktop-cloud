package com.pkpmdesktopcloud.desktopcloudbusiness.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.desktop.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ComponentDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.ProductDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubsDetailsDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.SubscriptionDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.dao.WorkOrderDAO;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.ProductInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsDetails;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.page.PageBean;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SubsDetailsService;

@Service
public class SubsDetailsServiceImpl implements SubsDetailsService {
	
	@Resource
	private SubscriptionDAO subscriptionMapper;
	
	@Resource
	private SubsDetailsDAO subsDetailsMapper;
	
	@Resource
	private ProductDAO productMapper;
	
	@Resource
	private ComponentDAO componentMapper;
	
	@Resource
	private WorkOrderDAO workOrderMapper;
	
	@Override
	public PageBean<MyProduct> showList(int userId,Integer currentPage,Integer pageSize) {
		
		PageHelper.startPage(currentPage, pageSize);
		
		
		List<MyProduct> myProducts = redisCacheUtil.getCacheList("MyProduct:"+userId);
		
		LocalDateTime nowTime = LocalDateTime.now();
		// 若存在Redis缓存，从缓存中读取
		if(myProducts!= null && myProducts.size()>0) {
			
			for (MyProduct myProduct : myProducts) {
				String invalid = myProduct.getInvalidtime();
				LocalDateTime invalidTime = DateUtils.string2LocalDateTime(invalid, "yyyy年MM月dd日  HH:mm:ss");
				boolean flagTime;
				if (nowTime.isAfter(invalidTime)) {
					flagTime = false;
				} else {
					flagTime = true;
				}
				myProduct.setFlagTime(flagTime);
			}
		} else {
			List<Long> subsIds = subscriptionMapper.findSubsId(userId);
			
			for (Long subsId : subsIds) {
				
				List<SubsDetails> subsDetails = subsDetailsMapper.findSubsDetailsList(subsId);
				
				for (SubsDetails subs : subsDetails) {
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
					List<ProductInfo> products = productMapper.getProductByProductId(productId);
					String productDesc = products.get(0).getProductDesc(); 
					List<String> componentNames = new ArrayList<>();
					List<String> hostIp = workOrderMapper.findHostIp(userId, subsId, productId);
					List<Integer> status = workOrderMapper.findStatus(userId, subsId, productId);
					
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
			
			redisCacheUtil.setCacheList("MyProduct:"+userId, myProducts);
		}
		
		PageBean<MyProduct> pageData = new PageBean<>(currentPage,pageSize,myProducts.size());
		pageData.setItems(myProducts);
		
		return pageData;
	}

}
