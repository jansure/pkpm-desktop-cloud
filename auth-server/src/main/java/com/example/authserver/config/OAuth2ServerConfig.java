package com.example.authserver.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * OAuth2 授权服务器配置类
 * @author pkpm
 *
 */
@Configuration
@EnableAuthorizationServer //用于配置 OAuth 2.0 授权服务器机制
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

	@Resource
	private AuthenticationManager authenticationManager;
	
	@Resource
    private RedisConnectionFactory connectionFactory;

    @Resource
    private OAuth2ClientProperties clientProperties;
    
//	@Autowired
//	private UserDetailsService userDetailsService;
	
    /**
     * 配置授权服务器端点，如令牌存储，令牌自定义，用户批准和授权类型，不包括端点安全配置
     */
    @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)   //直接注入一个AuthenticationManager，自动开启密码授权类型
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer());
    }

	/**
	 * 配置授权服务器端点的安全
	 */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                ;
    }

    /**
     * 配置客户端详情
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()	// 使用内存存储客户端信息
//                .withClient(clientProperties.getClientId())	// client_id
//                .secret(clientProperties.getClientSecret())	// client_secret
                .withClient("client1")	// client_id
                .secret("123456")	// client_secret
                .authorizedGrantTypes("refresh_token","authorization_code", "password", "client_credentials")	// 该client允许的授权类型, oauth2保护模式
                .scopes("read", "view", "webclient","mobileclient")	// 允许的授权范围
                .accessTokenValiditySeconds(3600) //设置token有效时间，单位是秒，(如果不设置，框架内部默认是12小时),失效后自动从redis中移除
                .refreshTokenValiditySeconds(3600) //设置refresh_token有效时间
                                                                                                                                                              .autoApprove(true);	//登录后绕过批准询问(/oauth/confirm_access)
    }

    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()	// 使用in-memory存储
//				.withClient("eagleeye")	// client_id
//				.secret("thisissecret")	// client_secret
//				.authorizedGrantTypes(
//						"refresh_token",
//						"password",
//						"client_credentials")	// 该client允许的授权类型
//				.scopes("webclient","mobileclient");	// 允许的授权范围
//	}
	
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.authenticationManager(authenticationManager)
//				.userDetailsService(userDetailsService);
//	}

//	@Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() // 使用in-memory存储
//                .withClient("client") // client_id
//                .secret("secret") // client_secret
//                .authorizedGrantTypes("authorization_code") // 该client允许的授权类型
//                .scopes("app"); // 允许的授权范围
//    }
}
