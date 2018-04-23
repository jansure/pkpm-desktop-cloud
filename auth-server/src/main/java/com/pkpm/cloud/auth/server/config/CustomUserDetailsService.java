package com.pkpm.cloud.auth.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pkpm.cloud.auth.server.client.LoginClient;
import com.pkpm.cloud.auth.server.entity.UserInfo;
import com.pkpm.cloud.auth.server.util.RequestUtils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息获取
 * @author pkpm
 *
 */
@Component("customUserDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private LoginClient loginClient;

    /**
     * 通过 Username 加载用户详情
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String password =  RequestUtils.getCurrentRequest().getParameter("password");
        Integer userId = loginClient.login(username, password);
        log.info("user_id : {}", userId);
        //return new User(userId == null ? username : userId.toString(), password, authorities);
        try {
			if (userId != 0) {
				UserDetails userDetails = new User(userId.toString(), password, authorities);
				return userDetails;
			}
			return new User(username,password, authorities);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new User(username,password, authorities);
    }
  /*  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String password =  RequestUtils.getCurrentRequest().getParameter("password");
        Integer i = loginClient.login(username, password);
       // logger.info("user_id : {}", userId);
        //return new User(userId == null ? username : userId.toString(), password, authorities);
        if (i != 0) {
        	UserDetails userDetails = new User(userInfo.getUserID().toString(), password, authorities);
        	return userDetails;
        }
        return null;
    }*/
}
