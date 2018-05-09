  
/**    
 * @Title: UserService.java  
 * @Package com.example.authserver.client  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author wangxiulong  
 * @date 2018年4月23日  
 * @version V1.0    
 */  
    
package com.pkpm.cloud.auth.server.service;

/**  
 * @ClassName: UserService  
 * @Description: 用户认证服务类
 * @author wangxiulong  
 * @date 2018年4月23日  
 *    
 */
public interface UserService {

	/**
	 * 
	 * @Title: isLogin  
	 * @Description: 判断是否登录
	 * @param @param token
	 * @param @return    参数  
	 * @return Boolean    返回类型  
	 * @throws
	 */
	Boolean isLogin(String token);
}
