package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.page.ResultObject;
import com.gateway.common.domain.PkpmOperatorStatus;
import com.google.common.base.Preconditions;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.MyProduct;
import com.pkpmdesktopcloud.desktopcloudbusiness.dto.WorkOrderVO;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubsDetailsService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubscriptionService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudUserInfoService;
import com.pkpmdesktopcloud.redis.RedisCache;

@RestController
@Api(description ="订单操作")
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Resource
	private PkpmCloudSubscriptionService subscription;
	
	@Resource
	private PkpmCloudUserInfoService userService;
	
	@Resource
	private PkpmCloudSubsDetailsService subsDetailsService;
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@ApiOperation("订单登陆状态检查")
	@RequestMapping(value="/immediatelyUse",method=RequestMethod.POST)
	public ResultObject immediatelyUse(@RequestBody WorkOrderVO wo,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Integer userId = wo.getUserId();
		Preconditions.checkNotNull(userId, "请您先登录账号才可以使用");
				
		PkpmCloudUserInfo userInfo = userService.findUser(userId);
		if(userInfo == null){
			
			return ResultObject.failure("请重新登录");
		}
		
		RedisCache cache = new RedisCache(subsDetailsService.MY_PRODUCT_ID);
		List<MyProduct> myProducts = (List<MyProduct>)cache.getObject(userId);
		
		if(myProducts != null && myProducts.size() > 5){
			
			return ResultObject.failure("您购买的条数已达到上限");
		}
		
		try {
			PkpmOperatorStatus pkpmOperatorStatus = subscription.saveSubsDetails(userInfo, wo);
			return ResultObject.success(pkpmOperatorStatus, "恭喜您申请免费使用成功,请稍等,马上为您开通！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResultObject.failure("创建订单失败,请重新创建订单");
		
	}

	@ApiOperation("订单状态更改")
	@PostMapping(value = "/setSubsStatus")
	public ResultObject updateSubscription(@RequestBody PkpmCloudSubscription subsCription){
		String response = subscription.updateSubsCriptionBySubsId(subsCription);
		return ResultObject.success(response);
	}
}
