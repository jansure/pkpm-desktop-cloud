package com.pkpmcloud.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.desktop.utils"})
public class FastdfsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FastdfsApplication.class, args);
	}
}
