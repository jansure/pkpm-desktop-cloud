package com.pkpmdesktopcloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
        String name = "desktop-cloud-business";
        String url = "http://127.0.0.1:8083/desktop-cloud-business/swagger-ui.html";
        String mail = "jansure@sina.com";
        return new ApiInfoBuilder()
                .title("glory cloud system")
                .description("Restful APIs for UI")
                .contact(new Contact(name,url,mail))
                .version("1.0")
                .build();
    }

}
