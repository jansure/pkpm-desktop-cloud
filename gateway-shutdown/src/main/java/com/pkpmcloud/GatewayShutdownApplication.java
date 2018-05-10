package com.pkpmcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author xuhe
 * @date 2018/05/09
 * 自动关机脚本
 */
@SpringBootApplication
public class GatewayShutdownApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayShutdownApplication.class, args);
	}
}
