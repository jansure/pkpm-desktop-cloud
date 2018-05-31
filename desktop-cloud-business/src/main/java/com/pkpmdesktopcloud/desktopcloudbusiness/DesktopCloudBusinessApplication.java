package com.pkpmdesktopcloud.desktopcloudbusiness;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
//@EnableScheduling
//@EnableEurekaClient
//@EnableFeignClients
@ComponentScan(basePackages={"com.pkpmdesktopcloud.desktopcloudbusiness","com.desktop.utils"})
@MapperScan(basePackages = "com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper", annotationClass = Mapper.class)
public class DesktopCloudBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesktopCloudBusinessApplication.class, args);
	}
	
	// 跨域配置
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}
	// 设置跨域过滤器
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
