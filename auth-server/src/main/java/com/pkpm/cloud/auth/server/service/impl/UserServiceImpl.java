  
/**    
 * @Title: UserService.java  
 * @Package com.example.authserver.client  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年4月23日  
 * @version V1.0    
 */  
    
package com.pkpm.cloud.auth.server.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import com.pkpm.cloud.auth.server.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**  
 * @ClassName: UserService  
 * @Description: 用户认证服务类
 * @author wangxiulong  
 * @date 2018年4月23日  
 *    
 */
@Component
@Slf4j
public class UserServiceImpl implements  UserService {

	@Resource
    private RedisTokenStore tokenStore;
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param token
	 * @return  
	 * @see com.example.authserver.client.UserService#isLogin(java.lang.String)  
	 */  
	@Override
	public Boolean isLogin(String token) {
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
		
		if(accessToken != null) {
			boolean isExpired = accessToken.isExpired();
			log.info("isExpired:{}, expiredDate:{}", isExpired, accessToken.getExpiration());
			User user = (User)authentication.getPrincipal();
			log.info("username:{}",user.getUsername());
			return true;
		}
		
		return false;
	}

	
}
