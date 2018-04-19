package com.example.authserver.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class LoginClientImpl implements LoginClient{

    public Integer login(@RequestParam("userNameOrTelephoneOrUserEmail") String username, @RequestParam("userLoginPassword") String password) {
    	
    	return 0;
    }
}
