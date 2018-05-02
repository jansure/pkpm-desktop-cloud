package com.pkpmdesktopcloud.desktopcloudbusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.pkpmdesktopcloud","com.desktop.utils"})
public class DesktopCloudBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesktopCloudBusinessApplication.class, args);
	}
}
