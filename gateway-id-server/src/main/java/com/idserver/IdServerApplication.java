package com.idserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@EnableSwagger2
@SpringBootApplication
public class IdServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdServerApplication.class);
    }
}
