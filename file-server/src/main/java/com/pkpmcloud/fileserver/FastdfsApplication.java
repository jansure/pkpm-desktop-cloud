package com.pkpmcloud.fileserver;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={"com.pkpmcloud.fileserver","com.desktop.utils"})
@Configuration
public class FastdfsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FastdfsApplication.class, args);
	}
	
    /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("1024MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("1024MB");  
        return factory.createMultipartConfig();  
    }  
}
