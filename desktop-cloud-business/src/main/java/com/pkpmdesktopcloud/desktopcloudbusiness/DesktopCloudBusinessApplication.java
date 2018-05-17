package com.pkpmdesktopcloud.desktopcloudbusiness;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@ComponentScan(basePackages={"com.desktop.utils","com.messageserver.messageserver.service"})
@MapperScan(basePackages = "com.pkpmdesktopcloud.desktopcloudbusiness.dao", annotationClass = Mapper.class)
public class DesktopCloudBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesktopCloudBusinessApplication.class, args);
	}
}
