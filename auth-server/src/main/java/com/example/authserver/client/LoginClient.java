package com.example.authserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pkpmcloud")
public interface LoginClient {

    @PostMapping("/user/login")
   Integer login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password);
   /* @PostMapping("/user/login")*/
    // UserInfo login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password);
}
