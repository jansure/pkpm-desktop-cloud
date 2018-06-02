package com.pkpmdesktopcloud.mediacontentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.pkpmdesktopcloud.mediacontentserver","com.desktop.utils"})
public class MediaContentServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaContentServerApplication.class, args);
	}
}
