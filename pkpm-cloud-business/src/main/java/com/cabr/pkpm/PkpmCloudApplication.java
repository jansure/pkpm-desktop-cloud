package com.cabr.pkpm;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@ComponentScan(basePackages={"com.cabr.pkpm","com.desktop.utils","com.messageserver.messageserver.service"})
public class PkpmCloudApplication {
	
    @Value("${server.port}")
    private Integer serverPort;
    
	public static void main(String[] args) {
		SpringApplication.run(PkpmCloudApplication.class, args);
	}
	
	 @Bean
	 @SuppressWarnings({"rawtypes","unchecked"})
	 public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		 RedisTemplate<Object, Object> template = new RedisTemplate<>();
		 template.setConnectionFactory(redisConnectionFactory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		 ObjectMapper om = new ObjectMapper();
		 om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		 om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		 jackson2JsonRedisSerializer.setObjectMapper(om);
		 
		 template.setValueSerializer(jackson2JsonRedisSerializer);
		 template.setKeySerializer(new StringRedisSerializer());
		return template;
		 
	 }
	 
	 
	/* @Bean
	 public EmbeddedServletContainerFactory servletContainer(){
		 TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
			 @Override
			protected void postProcessContext(Context context) {
				 
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/*");
				securityConstraint.addCollection(securityCollection);
				context.addConstraint(securityConstraint);
			}
		 };
		 
		 tomcat.addAdditionalTomcatConnectors(httpConnector());
		 return tomcat;
	 }
	 
	 @Bean
	 public Connector httpConnector(){
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol"); 
		connector.setScheme("http");
		//connector.setPort(8080);
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);
		
		return connector;
	 }*/
}
