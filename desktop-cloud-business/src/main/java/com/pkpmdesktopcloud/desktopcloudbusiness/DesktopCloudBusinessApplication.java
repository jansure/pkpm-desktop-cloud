package com.pkpmdesktopcloud.desktopcloudbusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages={"com.pkpmdesktopcloud.desktopcloudbusiness.dao.mapper","com.desktop.utils","com.messageserver.messageserver.service"})
public class DesktopCloudBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesktopCloudBusinessApplication.class, args);
	}
}
