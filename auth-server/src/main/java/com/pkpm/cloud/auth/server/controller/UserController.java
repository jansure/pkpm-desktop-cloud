package com.pkpm.cloud.auth.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pkpm.cloud.auth.server.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
    private UserService userService;
	
	@RequestMapping(value = "/testLogin")
	public Map<String, Object> user(String userName, String password) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("username", userName);

		return userInfo;
	}

	/**
	 * 
	 * @Title: login 
	 * @Description: 模拟远程登录 
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	public Integer login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password, HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("username:" + username);
		return 100;
		// if("aaa".equals(username)){
		//
		// result.set("登陆成功", 1, 100);
		// } else {
		// result.set("您输入的用户名或密码有误", 0);
		// }
		// return result;
	}

	@RequestMapping("/islogin")
	public Boolean isLogin(String token) {
		// 根据token获取用户信息
		// 判断登录状态
		return userService.isLogin(token);
	}
}
