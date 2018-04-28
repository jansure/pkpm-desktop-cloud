package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.SubsCription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.UserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.WorkOrderVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SubsDetailsService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.SubscriptionService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.UserService;
import com.pkpmdesktopcloud.redis.RedisCache;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Resource
	private SubscriptionService subscription;
	
	@Resource
	private UserService userService;
	
	@Resource
	private SubsDetailsService subsDetailsService;
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@RequestMapping(value="/immediatelyUse",method=RequestMethod.POST)
	public ResultObject immediatelyUse(@RequestBody WorkOrderVO wo,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Integer userId = wo.getUserId();
		Preconditions.checkNotNull(wo.getUserId(), "请您先登录账号才可以使用");
				
		UserInfo userInfo = userService.findUser(wo.getUserId());
		if(userInfo == null){
			
			return ResultObject.failure("请重新登录");
		}
		
		RedisCache cache = new RedisCache(subsDetailsService.MY_PRODUCT_ID);
		List<MyProduct> myProducts = (List<MyProduct>)cache.getObject(userId);
		
		if(myProducts != null && myProducts.size() > 5){
			
			return ResultObject.failure("您购买的条数已达到上限");
		}
		
		try {
			PkpmOperatorStatus pkpmOperatorStatus = subscription.saveSubsDetails(userInfo,wo);
			return ResultObject.success(pkpmOperatorStatus, "恭喜您申请免费使用成功,请稍等,马上为您开通！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultObject.failure("创建订单失败,请重新创建订单");
		
	}

	@PostMapping(value = "/setSubsStatus")
	public ResultObject updateSubscription(@RequestBody SubsCription subsCription){
		String response = subscription.updateSubsCriptionBySubsId(subsCription);
		return ResultObject.success(response);
	}
}
