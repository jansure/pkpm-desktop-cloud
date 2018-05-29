package com.pkpmdesktopcloud.desktopcloudbusiness;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
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
}
