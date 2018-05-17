/*
package com.pkpmdesktopcloud.desktopcloudbusiness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * @author xuhe
 * @description
 * @date 2018/5/17
 *//*

@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
public class Swagger2Config {
    */
/**
     * 添加摘要信息(Docket)
     *//*

    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：构力_构力云业务系统_接口文档")
                        .description("描述：用于创建云桌面的网页端,具体包括XXX,XXX模块...")
                        .contact(new Contact("构力创新中心", "http://49.4.8.123:81/", "evanxuhe@163.com"))
                        .version("版本号:1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pkpmdesktopcloud.desktopcloudbusiness.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
*/
