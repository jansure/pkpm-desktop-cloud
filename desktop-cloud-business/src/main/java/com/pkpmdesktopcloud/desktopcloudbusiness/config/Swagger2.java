package com.pkpmdesktopcloud.desktopcloudbusiness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author xuhe
 * @description
 * @date 2018/5/17
*/



@Configuration //标记配置类
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pkpmdesktopcloud.desktopcloudbusiness.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        String name = "构力云桌面";
        String url = "http://49.4.8.123:8083/desktop-cloud-business/swagger-ui.html#";
        String mail = "jansure@sina.com";
        return new ApiInfoBuilder()
                .title("构力云桌面网页端API文档")
                .description("内部资料，未经允许，严禁外传")
                .version("1.0")
                .build();
    }

}
