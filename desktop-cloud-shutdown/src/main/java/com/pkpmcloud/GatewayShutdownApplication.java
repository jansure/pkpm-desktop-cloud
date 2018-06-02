package com.pkpmcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuhe
 * @date 2018/05/09
 * 自动关机脚本
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.pkpmcloud","com.desktop.utils"})
@Configuration
public class GatewayShutdownApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayShutdownApplication.class, args);
	}
}
