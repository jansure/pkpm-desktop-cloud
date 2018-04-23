package com.pkpm.cloud.auth.server.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "pkpm-desktop-cloud")
@FeignClient(name = "pkpm-desktop-cloud", url = "http://localhost:18070/")
public interface LoginClient {

//    @PostMapping("/user/login")
    @RequestMapping(value = "/uaa/user/login", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
   Integer login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password);
   /* @PostMapping("/user/login")*/
    // UserInfo login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password);
}
