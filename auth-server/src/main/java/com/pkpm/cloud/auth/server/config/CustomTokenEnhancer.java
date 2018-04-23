package com.pkpm.cloud.auth.server.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
/**
 * 通过使用TokenEnhancer来修改授权服务器返回token的内容.
 */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Map<String, Object> additional = new HashMap<>();
        try {
			additional.put("user_id", Integer.parseInt(user.getUsername()));
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additional);
			return accessToken;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			additional.put("user_id", Integer.parseInt(user.getUsername()));
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additional);
			return accessToken;
		}
    }

}
