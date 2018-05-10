package com.pkpmcloud.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FastdfsApplication {
	public static void main(String[] args) {
		SpringApplication.run(FastdfsApplication.class, args);
	}
}
