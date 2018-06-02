package com.pkpm.cloud.config.server;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@Configurable
@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
public class ConfigServerApplication {

    public static void main( String[] args ) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
