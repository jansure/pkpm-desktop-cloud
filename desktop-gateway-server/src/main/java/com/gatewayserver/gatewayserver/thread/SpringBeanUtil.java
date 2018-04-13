/**
 *
 */
package com.gatewayserver.gatewayserver.thread;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yangpengfei
 * @Description 直接通过Spring 上下文获取SpringBean,用于多线程环境
 * @time 2018年3月29日 下午3:35:50
 */
// @Configuration
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext context) {
        SpringBeanUtil.applicationContext = context;
    }
}