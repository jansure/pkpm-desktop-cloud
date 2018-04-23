package com.gateway.cloud.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import com.gateway.common.dto.user.UserInfoForChangePassword;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.utils.HttpConfigBuilder;
import com.desktop.utils.JsonUtil;
import com.gateway.cloud.business.entity.SubsCription;
import com.gateway.cloud.business.entity.UserInfo;
import com.gateway.cloud.business.entity.WorkOrder;
import com.gateway.cloud.business.service.SubscriptionService;
import com.gateway.cloud.business.service.UserService;
import com.gateway.cloud.business.service.WorkOrderService;
import com.gateway.cloud.business.utils.Base64Utils;
import com.gateway.cloud.business.utils.ResponseResult;
import com.gateway.cloud.business.utils.ResultObject;
import com.gateway.cloud.business.utils.StringUtil;
import com.gateway.cloud.business.utils.sdk.ClientDemo;
import com.gateway.common.domain.CommonRequestBean;
import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.MyHttpResponse;
import com.pkpm.httpclientutil.common.HttpMethods;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private SubscriptionService subscriptionService;
	
	
	@Value("${server.host}")
	private String serverHost;

	protected ResponseResult result = new ResponseResult();
	
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 用户注册
	 * 
	 * @param userInfo(user_name,password,user_mobile_number)
	 * @return 注册成功的页面
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResponseResult regist(@RequestBody Map<String,String> map,
			 HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String checkCode = map.get("checkCode");
		String userMobileNumber = map.get("userMobileNumber");
		String userName = map.get("userName");
		String userLoginPassword = map.get("userLoginPassword");
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String realCheckCode = stringRedisTemplate.opsForValue().get(userMobileNumber);
		if (StringUtils.isBlank(checkCode)) {
			this.result.set("验证码不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		if (StringUtils.isBlank(userMobileNumber)) {
			this.result.set("手机号不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		if (StringUtils.isBlank(userName)) {
			this.result.set("用户名不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		
		if (StringUtils.isBlank(userLoginPassword)) {
			this.result.set("密码不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		boolean flag = StringUtil.checkPassword(userName, userLoginPassword);
		if( !flag ){
			this.result.set("您输入的密码不合法,请重新输入!", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserMobileNumber(userMobileNumber);

		if (checkCode != null && checkCode.equals(realCheckCode)) {
			stringRedisTemplate.delete(userMobileNumber);
			userLoginPassword = Base64Utils.b64FromString(userLoginPassword);
			userInfo.setUserLoginPassword(userLoginPassword);
			userService.saveUserInfo(userInfo);
			this.result.set("恭喜您注册成功", 1);
			logger.debug(this.result.getMessage());
		} else {
			response.setContentType("text/html;charset=utf-8");
			this.result.set("验证码错误", 0);
		}
		return this.result;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = {"application/json;charset=utf-8"})
	public ResponseResult login(@RequestBody Map<String,String> map,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
        String username = map.get("username");
        String password = map.get("password");
		if(StringUtils.isBlank(username)){
			this.result.set("登录账号不能为空",0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		if (StringUtils.isBlank(password)) {
			this.result.set("密码不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		UserInfo realUserInfo = userService.findByUserNameOrTelephoneOrUserEmail(username);
		
		password = Base64Utils.b64FromString(password);
		if(realUserInfo!=null&&password.equals(realUserInfo.getUserLoginPassword())){
			/*Map<String,String> mapInfo = new HashMap<String,String>();
			mapInfo.put("userId", realUserInfo.getUserID().toString());
			mapInfo.put("username", realUserInfo.getUserName());
			this.result.set("登陆成功", 1,mapInfo );*/
			this.result.set("登陆成功", 1,realUserInfo.getUserID() );
		} else {
			this.result.set("您输入的用户名或密码有误", 0);
		}
		return this.result;
	}
    
	/**
	 * 
	 * @param session
	 * @return ResponseResult
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getUserName", method = RequestMethod.POST)
	public ResponseResult getUserName(HttpServletResponse response,@RequestBody Map<String,String> map){
		String strUserId = map.get("userId");
		Integer userId = Integer.parseInt(strUserId);
		response.setHeader("Access-Control-Allow-Origin", "*");
		if(userId == null){
			this.result.set("请您先登录", 0);
			return this.result;
		}
		UserInfo user = userService.findUser(userId);
		if(user == null){
			this.result.set("请您先登录", 0);
			return this.result;
		}
		String userName = user.getUserName();
		 userId = user.getUserID();
		 Map<String,String> strmap = new HashMap<>();
		 strmap.put("userId", Integer.toString(userId));
		 strmap.put("userName", userName);
		this.result.set("已登录", 1, strmap);
		return this.result;
		
	}
	/**
	 * 发送验证码短信
	 * 
	 * @param user_mobile_number
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public ResponseResult sendMessage(@RequestBody Map<String,String> map, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userMobileNumber = map.get("userMobileNumber");
		if (StringUtils.isBlank(userMobileNumber)) {
			this.result.set("手机号不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		try {
			String checkCode = RandomStringUtils.randomNumeric(4);
			stringRedisTemplate.opsForValue().set(userMobileNumber, checkCode, 60 * 15, TimeUnit.SECONDS);
			String message = "您的短信验证码是:" + checkCode + "，请注意查收";
			ClientDemo clientDemo = new ClientDemo();
			clientDemo.smsPublish(userMobileNumber, message);
			this.result.set("发送短信成功", 1);
			logger.debug(this.result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信产异常:" + e);
			this.result.set("发送短信失败", 0);
		}

		return this.result;
	}

	/**
	 * 异步校验邮箱，是否存在
	 * 
	 * @param userEmailOrMobileNumber
	 */
	@RequestMapping(value = "/findByEmailOrUserMobileNumber", method = RequestMethod.POST)
	public ResponseResult findByEmailOrUserMobileNumber(@RequestBody Map<String,String> map , HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userEmailOrMobileNumber = map.get("userName");
		String typeStr = map.get("type");
		Integer type = Integer.parseInt(typeStr);
		if (StringUtils.isBlank(userEmailOrMobileNumber)) {
			this.result.set("请输入账户名", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		UserInfo realUserInfo = userService.findByUserNameOrTelephoneOrUserEmail(userEmailOrMobileNumber);
		if (realUserInfo != null) {
			if(type == 1){
				this.result.set("该手机号码已被注册,请重新输入", 0);
			}else{
				this.result.set("该用户名已被注册,请重新输入", 0);
			}
			logger.debug(this.result.getMessage());
		} else {
			this.result.set("可以注册", 1);
			logger.debug(this.result.getMessage());
		}
		return this.result;
	}

	/**
	 * 完善个人信息,获取会员名称和联系方式
	 * 
	 * @param session
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/perfectInfoInit", method = RequestMethod.POST)
	public ResponseResult perfectInfoInit(@RequestBody Map<String, String> map, HttpServletResponse response) {

		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		UserInfo user = userService.findUser(new Integer(map.get("userID")));

		if (user != null) {
			this.result.set("获取个人信息成功", 1, user);
		} else {
			this.result.set("获取个人信息失败", 0);
		}

		return this.result;
	}

	/**
	 * 完善个人信息，更新数据
	 * 
	 * @param userInfo(user_id,user_identification_card,user_identification_name,user_organization)
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/perfectInfo", method = RequestMethod.POST)
	public ResponseResult perfectInfo(@RequestBody UserInfo userInfo, HttpServletResponse response) {
	// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Integer userID = userInfo.getUserID();
		
		if (StringUtils.isBlank(userInfo.getUserIdentificationCard()) && StringUtils.isBlank(userInfo.getUserIdentificationName())
				&& StringUtils.isBlank(userInfo.getUserOrganization())) {
			this.result.set("至少选择一项填写", 0);
			return this.result;
		}
		
		// 
		if (StringUtils.isNotBlank(userInfo.getUserIdentificationCard()) && userInfo.getUserIdentificationCard().length()>18) {
			this.result.set("身份证号长度不能大于18", 0);
			return this.result;
		}
		if (StringUtils.isNotBlank(userInfo.getUserIdentificationName()) && userInfo.getUserIdentificationName().length()>40) {
			this.result.set("姓名长度不能大于40", 0);
			return this.result;
		}
		if (StringUtils.isNotBlank(userInfo.getUserOrganization()) && userInfo.getUserOrganization().length()>45) {
			this.result.set("公司名称长度不能大于45", 0);
			return this.result;
		}
		
		String userIdentificationCard = StringUtils.deleteWhitespace(userInfo.getUserIdentificationCard());
		String userIdentificationName = StringUtils.deleteWhitespace(userInfo.getUserIdentificationName());
		String userOrganization = StringUtils.deleteWhitespace(userInfo.getUserOrganization());
		
		UserInfo user = userService.findUser(userID);
		if (StringUtils.isNotBlank(userIdentificationCard)) {
			user.setUserIdentificationCard(userIdentificationCard);
		}
		if (StringUtils.isNotBlank(userIdentificationName)) {
			user.setUserIdentificationName(userIdentificationName);
		}
		if (StringUtils.isNotBlank(userOrganization)) {
			user.setUserOrganization(userOrganization);
		}
	
		if (!userService.updateUserInfo(user)) {
			this.result.set("更新失败", 0);
		} else {
			this.result.set("更新成功", 1, user);
		}
		return this.result;

	}
	
	/**
	 * 修改手机号码
	 * 
	 * @param session
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/changMobileNumber", method = RequestMethod.POST)
	public ResponseResult changMobileNumber(@RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {

		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Integer userID = new Integer(map.get("userID"));
		String oldMobileNumber = map.get("oldMobileNumber");
		String checkCode = map.get("checkCode");
		String newMobileNumber = map.get("newMobileNumber");
		String password = map.get("password");
		
		if (StringUtils.isBlank(checkCode)) {
			this.result.set("验证码不能为空", 0);
			return this.result;
		}

		if (StringUtils.isBlank(newMobileNumber)) {
			this.result.set("手机号不能为空", 0);
			return this.result;
		}
		
		if (!newMobileNumber.equals(StringUtils.deleteWhitespace(newMobileNumber))) {
			this.result.set("手机号不能带空格", 0);
			return this.result;
		}
		
		if (StringUtils.isBlank(password)) {
			this.result.set("密码不能为空", 0);
			return this.result;
		}

		UserInfo user = userService.findUser(userID);
		
		// 从数据库中查出password并解密
		String userPassword = user.getUserLoginPassword();
		
		String realPassword = Base64Utils.stringFromB64(userPassword);

		String userMobileNumber = user.getUserMobileNumber();
		
		//获取真正验证码
		String realCheckCode = stringRedisTemplate.opsForValue().get(userMobileNumber);
		
		if (!userMobileNumber.equals(oldMobileNumber)) {
			this.result.set("原手机号输入错误", 0);
			return this.result;
		}
		
		if (!checkCode.equals(realCheckCode)) {
			this.result.set("验证码输入错误", 0);
			return this.result;
		}
		
		if (!realPassword.equals(password)) {
			this.result.set("密码输入错误", 0);
			return this.result;
		}
		
		if (userService.findByUserNameOrTelephoneOrUserEmail(newMobileNumber)!=null) {
			this.result.set("该手机号已存在，请换一个手机号", 0);
			return this.result;
		}

		user.setUserMobileNumber(newMobileNumber);
		
		List<WorkOrder> workOrders = workOrderService.findWorkOrderListByUserId(userID);
		
		if(workOrders!=null && !workOrders.isEmpty()){
			if (!userService.updateUserInfo(user) || !workOrderService.updatePasswordOrMobileNumber(userID, null, newMobileNumber)) {
				this.result.set("手机号修改失败，请再试一次", 0);
				return this.result;
			}
		}else{
			if (!userService.updateUserInfo(user)) {
				this.result.set("密码修改失败", 0);
				return this.result;
			}
		}
		
		this.result.set("手机号修改成功", 1, user);
		return this.result;
	}
	
	/**
	 * 找回密码
	 * 
	 * @param session
	 * @throws Exception 
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/getBackPassword", method = RequestMethod.POST)
	public ResponseResult getBackPassword(@RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {	
		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String mobileNumber = map.get("mobileNumber");

		if (StringUtils.isBlank(mobileNumber)) {
			this.result.set("手机号不能为空", 0);
			logger.debug(this.result.getMessage());
			return this.result;
		}
		
		String checkCode = map.get("checkCode");
		
		if (StringUtils.isBlank(checkCode)) {
			this.result.set("验证码不能为空", 0);
			return this.result;
		}
		
		UserInfo user = userService.findByUserNameOrTelephoneOrUserEmail(mobileNumber);
		
		if(user == null){
			this.result.set("您的手机号尚未注册，快去注册吧", 0);
			return this.result;
		}

		//获取真正验证码
		String realCheckCode = stringRedisTemplate.opsForValue().get(mobileNumber);
		
		if (!checkCode.equals(realCheckCode)) {
			this.result.set("验证码输入错误", 0);
			return this.result;
		}
		
		try {
			String password = Base64Utils.stringFromB64(user.getUserLoginPassword());
			String message = "您的密码是:" + password + "，请记住密码";
			ClientDemo clientDemo = new ClientDemo();
			clientDemo.smsPublish(mobileNumber, message);
			this.result.set("您的密码将以短信的形式发送到您的手机上，请注意查收", 1);
			logger.debug(this.result.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信产异常:" + e);
			this.result.set("发送短信失败", 0);
		}
		return this.result;
	}

	/**
	 * 修改密码
	 *
	 * @param session
	 * @throws Exception
	 * @return ResponseResult
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResultObject changPassword(@RequestBody UserInfoForChangePassword newUserInfo) throws Exception {
		Integer userId = newUserInfo.getUserId();
		List<SubsCription> subsCriptionList = subscriptionService.findSubsCriptionByUserId(userId);
		logger.info(subsCriptionList);
		userService.changeUserPassword(newUserInfo, subsCriptionList);
		return ResultObject.success("密码修改成功");
	}
}
