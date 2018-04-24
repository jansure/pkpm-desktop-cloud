package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gateway.cloud.business.entity.SubsCription;
import com.gateway.cloud.business.entity.UserInfo;
import com.gateway.cloud.business.service.SubscriptionService;
import com.gateway.cloud.business.service.UserService;
import com.gateway.cloud.business.utils.ResponseResult;
import com.gateway.cloud.business.utils.ResultObject;
import com.gateway.cloud.business.utils.sdk.RedisCacheUtil;
import com.gateway.cloud.business.vo.MyProduct;
import com.gateway.cloud.business.vo.WorkOrderVO;
import com.gateway.common.domain.PkpmOperatorStatus;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscription;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisCacheUtil<MyProduct> redisCacheUtil;
	
	protected ResponseResult result = new ResponseResult();
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@RequestMapping(value="/immediatelyUse",method=RequestMethod.POST)
	public ResponseResult immediatelyUse(@RequestBody WorkOrderVO wo,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Integer userId = wo.getUserId();
		if(userId == null){
			this.result.set("请您先登录账号才可以使用", 2);
			return this.result;
		}
		UserInfo userInfo = userService.findUser(wo.getUserId());
		if(userInfo == null){
			this.result.set("请重新登录", 2);
			return this.result;
		}
		
		List<MyProduct> myProducts = redisCacheUtil.getCacheList("MyProduct:"+userId);
		if(myProducts != null && myProducts.size() > 0){
			if(myProducts.size() >= 5){
				this.result.set("您购买的条数已达到上限", 0);
				return this.result;
			}
		}
		
		try {
			PkpmOperatorStatus pkpmOperatorStatus = subscription.saveSubsDetails(userInfo,wo);
			this.result.set("恭喜您申请免费使用成功,请稍等,马上为您开通！", 1, pkpmOperatorStatus);
			return this.result;
		} catch (Exception e) {
			e.printStackTrace();
			//this.result.set("创建订单失败,请重新创建订单", 0);
			this.result.set(e.getMessage(), 0);
			return this.result;
		}
		
	}

	@PostMapping(value = "/setSubsStatus")
	public ResultObject updateSubscription(@RequestBody SubsCription subsCription){
		String response = subscription.updateSubsCriptionBySubsId(subsCription);
		return ResultObject.success(response);
	}
}
