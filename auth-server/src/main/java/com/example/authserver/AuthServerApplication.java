package com.example.authserver;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class AuthServerApplication {

	@RequestMapping(value="/user", produces="application/json")
	public Map<String, Object> user(OAuth2Authentication user) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("userid", user.getName());
		Map<String, String> parametersMmap = user.getOAuth2Request().getRequestParameters();
		String username = parametersMmap.get("username");
		userInfo.put("username", username);
		
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
	 
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}
