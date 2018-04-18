package com.example.authserver.client;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@RequestMapping(value="/testLogin")
	public Map<String, Object> user(String userName, String password) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("username", userName);
		
		return userInfo;
	}
		/*public Map<String, String> user(OAuth2Authentication user,String token) throws Exception {
		Map<String, String> userInfo = new HashMap<>();
		if(StringUtils.isEmpty(token)){
			userInfo.put("msg","false" );
			return userInfo;
		}
		userInfo.put("userid", user.getName());
		String username;
		try {
			Map<String, String> parametersMmap = user.getOAuth2Request().getRequestParameters();
			username = parametersMmap.get("username");
			userInfo.put("username", username);
			userInfo.put("msg", "true");
		} catch (Exception e) {
			e.printStackTrace();
			userInfo.put("msg", "false");
		}
		
		return userInfo;
	}*/
	
	//@RequestMapping(value="/userP")
	@RequestMapping(value="/userP",method=RequestMethod.POST)
	public Principal userP(Principal user) {
		return user;
	}
	
/*	@RequestMapping("/islogin")
	public Boolean isLogin(String token)
	{
		//根据token获取用户信息
		//判断登录状态
		
		return Boolean.TRUE;
	}*/
}
