package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desktop.constant.MessageTypeEnum;
import com.desktop.utils.Base64Util;
import com.desktop.utils.StringUtil;
import com.desktop.utils.page.ResultObject;
import com.gateway.common.dto.user.UserInfoForChangePassword;
import com.google.common.base.Preconditions;
import com.messageserver.messageserver.service.impl.SmsMessageSenderImpl;
import com.messageserver.messageserver.service.message.Message;
import com.messageserver.messageserver.service.message.MessageSender;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudSubscription;
import com.pkpmdesktopcloud.desktopcloudbusiness.domain.PkpmCloudUserInfo;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudSubscriptionService;
import com.pkpmdesktopcloud.desktopcloudbusiness.service.PkpmCloudUserInfoService;
import com.pkpmdesktopcloud.redis.RedisCache;

@RestController
@Api(description ="用户操作")
@RequestMapping("/user")
public class UserController {
	
	//Redis Cache ID： 验证码
	private static final String REAL_CHECK_CODE_ID = "realCheckCode";

	@Autowired
	private PkpmCloudUserInfoService userService;
	
	@Autowired
	private PkpmCloudSubscriptionService subscriptionService;
	
//	@Resource
//	private SmsMessageSenderImpl smsMessageSender;
//

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 用户注册
	 * 
	 * @param userInfo(user_name,password,user_mobile_number)
	 * @return 注册成功的页面
	 */
	@ApiOperation("用户注册")
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ResultObject regist(String checkCode, String userMobileNumber, String userName, String userLoginPassword,
			 HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		
		
		Preconditions.checkArgument(StringUtils.isNotEmpty(checkCode),"验证码不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(userMobileNumber),"手机号不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(userName),"用户名不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(userLoginPassword),"密码不能为空");
		
		RedisCache cache = new RedisCache(REAL_CHECK_CODE_ID);
		String realCheckCode = (String)cache.getObject(userMobileNumber);
		
		if (!checkCode.equals(realCheckCode)) {
			response.setContentType("text/html;charset=utf-8");
			return ResultObject.failure("验证码错误");
		}
		
		boolean flag = StringUtil.checkPassword(userName, userLoginPassword);
		if( !flag ){
			
			return ResultObject.failure("您输入的密码不合法,请重新输入!");
		}
		
		
		PkpmCloudUserInfo userInfo = new PkpmCloudUserInfo();
		userInfo.setUserName(userName);
		userInfo.setUserMobileNumber(userMobileNumber);
		
		userLoginPassword = Base64Util.b64FromString(userLoginPassword);
		userInfo.setUserLoginPassword(userLoginPassword);
		userService.saveUserInfo(userInfo);
		
		cache.removeObject(userMobileNumber);
		
		return ResultObject.success("恭喜您注册成功");
		
		
	}
	@ApiOperation("用户登陆")
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = {"application/json;charset=utf-8"})
	public ResultObject login(String username, String password, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
  
		Preconditions.checkArgument(StringUtils.isNotEmpty(username), "登录账号不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空");
		
		PkpmCloudUserInfo realUserInfo = userService.findByUserNameOrTelephoneOrUserEmail(username);
		if(realUserInfo == null) {
			return ResultObject.failure("账号不存在");
		}
		
		password = Base64Util.b64FromString(password);
		
		if(!password.equals(realUserInfo.getUserLoginPassword())){
			return ResultObject.failure("密码有误");
		}
		return ResultObject.success(realUserInfo.getUserId(), "登陆成功");
	}
    
	/**
	 * 
	 * @param session
	 * @return ResultObject
	 */
	@SuppressWarnings("unused")
	@ApiOperation("获取用户名")
	@RequestMapping(value = "/getUserName", method = RequestMethod.POST)
	public ResultObject getUserName(HttpServletResponse response, Integer userId){
	
		response.setHeader("Access-Control-Allow-Origin", "*");
		Preconditions.checkNotNull(userId, "请您先登录");
		
		PkpmCloudUserInfo user = userService.findUser(userId);
		Preconditions.checkNotNull(user, "请您先登录");
		
		String userName = user.getUserName();
		Map<String,String> strmap = new HashMap<>();
		strmap.put("userId", Integer.toString(userId));
		strmap.put("userName", userName);
		
		return ResultObject.success(strmap, "已登录");
		
	}
	/**
	 * 发送验证码短信
	 * 
	 * @param user_mobile_number
	 * @return ResultObject
	 */
	@ApiOperation("发送短信")
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public ResultObject sendMessage(String userMobileNumber, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Preconditions.checkArgument(StringUtils.isNotEmpty(userMobileNumber), "手机号不能为空");
		
		try {
			String checkCode = RandomStringUtils.randomNumeric(4);
			
			RedisCache cache = new RedisCache(REAL_CHECK_CODE_ID);
			cache.setTimeOut(60 * 15);
			cache.putObject(userMobileNumber, checkCode);
			
			String message = "您的短信验证码是:" + checkCode + "，请注意查收";
			
			/*Message sendMessage = new Message();
	    	//消息类型为短信
			sendMessage.setMessageType(MessageTypeEnum.sms.toString());
	    	//消息接收人
			sendMessage.setTo(userMobileNumber);
	    	//消息内容
			sendMessage.setContent(message);
			
//	    	MessageSender smsMessageSender = new SmsMessageSenderImpl();
			smsMessageSender.sendMessage(sendMessage);*/
			
			return ResultObject.success("发送短信成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信产异常:" + e);
		}

		return ResultObject.failure("发送短信失败");
	}

	/**
	 * 异步校验邮箱，是否存在
	 * 
	 * @param userEmailOrMobileNumber
	 */
	@ApiOperation("异步邮箱校验")
	@RequestMapping(value = "/findByEmailOrUserMobileNumber", method = RequestMethod.POST)
	public ResultObject findByEmailOrUserMobileNumber(String userName, Integer type, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Preconditions.checkArgument(StringUtils.isNotEmpty(userName), "请输入账户名");
		Preconditions.checkNotNull(type, "type不能为空");
		
		PkpmCloudUserInfo realUserInfo = userService.findByUserNameOrTelephoneOrUserEmail(userName);
		if (realUserInfo == null) {
			return ResultObject.success("可以注册");
		}
		
		String errorMsg = "该用户名已被注册,请重新输入";
		if(type == 1){
			errorMsg = "该手机号码已被注册,请重新输入";
		}
		
		return ResultObject.failure(errorMsg);
	}

	/**
	 * 完善个人信息,获取会员名称和联系方式
	 * 
	 * @param session
	 * @return ResultObject
	 */
	@ApiOperation("完善用户信息")
	@RequestMapping(value = "/perfectInfoInit", method = RequestMethod.POST)
	public ResultObject perfectInfoInit(Integer userID, HttpServletResponse response) {

		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		Preconditions.checkNotNull(userID, "userID不能为空");
		
		PkpmCloudUserInfo user = userService.findUser(userID);

		if (user != null) {
			return ResultObject.success(user, "获取个人信息成功");
		}
		
		return ResultObject.failure("获取个人信息失败");
	}

	/**
	 * 完善个人信息，更新数据
	 * 
	 * @param userInfo(user_id,user_identification_card,user_identification_name,user_organization)
	 * @return ResultObject
	 */
	@ApiOperation("更新用户信息")
	@RequestMapping(value = "/perfectInfo", method = RequestMethod.POST)
	public ResultObject perfectInfo(@RequestBody PkpmCloudUserInfo userInfo, HttpServletResponse response) {
	// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Integer userID = userInfo.getUserId();
		
		if (StringUtils.isBlank(userInfo.getUserIdentificationCard()) && StringUtils.isBlank(userInfo.getUserIdentificationName())
				&& StringUtils.isBlank(userInfo.getUserOrganization())) {
			return ResultObject.failure("至少选择一项填写");
		}
		
		// 
		if (StringUtils.isNotBlank(userInfo.getUserIdentificationCard()) && userInfo.getUserIdentificationCard().length()>18) {
			return ResultObject.failure("身份证号长度不能大于18");
		}
		
		if (StringUtils.isNotBlank(userInfo.getUserIdentificationName()) && userInfo.getUserIdentificationName().length()>40) {
			return ResultObject.failure("姓名长度不能大于40");
		}
		if (StringUtils.isNotBlank(userInfo.getUserOrganization()) && userInfo.getUserOrganization().length()>45) {
			return ResultObject.failure("公司名称长度不能大于45");
		}
		
		String userIdentificationCard = StringUtils.deleteWhitespace(userInfo.getUserIdentificationCard());
		String userIdentificationName = StringUtils.deleteWhitespace(userInfo.getUserIdentificationName());
		String userOrganization = StringUtils.deleteWhitespace(userInfo.getUserOrganization());
		
		PkpmCloudUserInfo user = userService.findUser(userID);
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

			return ResultObject.failure("更新失败");
		}
		
		return ResultObject.success(user, "更新成功");

	}
	
	/**
	 * 修改手机号码
	 * 
	 * @param session
	 * @return ResultObject
	 */
	@ApiOperation("更换手机号")
	@RequestMapping(value = "/changMobileNumber", method = RequestMethod.POST)
	public ResultObject changMobileNumber(@RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {

		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		Preconditions.checkArgument(map != null && map.size() >= 5, "参数个数不对");
		Integer userID = new Integer(map.get("userID"));
		String oldMobileNumber = map.get("oldMobileNumber");
		String checkCode = map.get("checkCode");
		String newMobileNumber = map.get("newMobileNumber");
		String password = map.get("password");
		
		Preconditions.checkArgument(StringUtils.isNotEmpty(checkCode), "验证码不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(newMobileNumber), "手机号不能为空");
		Preconditions.checkArgument(newMobileNumber.equals(StringUtils.deleteWhitespace(newMobileNumber)), "手机号不能带空格");
		Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空");
		
		PkpmCloudUserInfo user = userService.findUser(userID);
		
		// 从数据库中查出password并解密
		String userPassword = user.getUserLoginPassword();
		
		String realPassword = Base64Util.stringFromB64(userPassword);

		String userMobileNumber = user.getUserMobileNumber();
		
		//获取真正验证码
		RedisCache cache = new RedisCache(REAL_CHECK_CODE_ID);
		String realCheckCode = (String)cache.getObject(userMobileNumber);
		
		Preconditions.checkArgument(userMobileNumber.equals(oldMobileNumber), "原手机号输入错误");
		Preconditions.checkArgument(checkCode.equals(realCheckCode), "验证码输入错误");
		Preconditions.checkArgument(realPassword.equals(password), "密码输入错误");
		
		PkpmCloudUserInfo userInfo = userService.findByUserNameOrTelephoneOrUserEmail(newMobileNumber);
		Preconditions.checkNotNull(userInfo, "该手机号已存在，请换一个手机号");
		
		user.setUserMobileNumber(newMobileNumber);
		
		if (!userService.updateUserInfo(user)) {
			
			return ResultObject.failure("手机号修改失败，请再试一次");
		} else{
			if (!userService.updateUserInfo(user)) {
				return ResultObject.failure("密码修改失败");
			}
		}
		
		return ResultObject.success(user, "手机号修改成功");
	}
	
	/**
	 * 找回密码
	 * 
	 * @param session
	 * @throws Exception 
	 * @return ResultObject
	 */
	@ApiOperation("获取加密密码")
	@RequestMapping(value = "/getBackPassword", method = RequestMethod.POST)
	public ResultObject getBackPassword(@RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {	
		// 允许跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		Preconditions.checkArgument(map != null && map.size() >= 2, "参数个数不对");
		
		String mobileNumber = map.get("mobileNumber");
		String checkCode = map.get("checkCode");
		Preconditions.checkArgument(StringUtils.isNotEmpty(mobileNumber), "手机号不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(checkCode), "验证码不能为空");
		
		//获取真正验证码
		RedisCache cache = new RedisCache(REAL_CHECK_CODE_ID);
		String realCheckCode = (String)cache.getObject(mobileNumber);
		if (!checkCode.equals(realCheckCode)) {

			return ResultObject.failure("验证码输入错误");
		}
				
		PkpmCloudUserInfo user = userService.findByUserNameOrTelephoneOrUserEmail(mobileNumber);
		if(user == null){

			return ResultObject.failure("您的手机号尚未注册，快去注册吧");
		}
		
		try {
			String password = Base64Util.stringFromB64(user.getUserLoginPassword());
			String message = "您的密码是:" + password + "，请记住密码";
			
			Message sendMessage = new Message();
	    	//消息类型为短信
			sendMessage.setMessageType(MessageTypeEnum.sms.toString());
	    	//消息接收人
			sendMessage.setTo(mobileNumber);
	    	//消息内容
			sendMessage.setContent(message);
			
//	    	MessageSender smsMessageSender = new SmsMessageSenderImpl();
//			smsMessageSender.sendMessage(sendMessage);
			
			return ResultObject.success("您的密码将以短信的形式发送到您的手机上，请注意查收");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信产异常:" + e);
		}
		
		return ResultObject.failure("发送短信失败");
	}

	/**
	 * 修改密码
	 *
	 * @param session
	 * @throws Exception
	 * @return ResultObject
	 */
	@ApiOperation("更新用户密码")
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResultObject changPassword(@RequestBody UserInfoForChangePassword newUserInfo) throws Exception {
		
		Integer userId = newUserInfo.getUserId();
		List<PkpmCloudSubscription> subsCriptionList = subscriptionService.findSubsCriptionByUserId(userId);
		
		logger.info(subsCriptionList);
		userService.changeUserPassword(newUserInfo, subsCriptionList);
		
		return ResultObject.success("密码修改成功");
	}
}
