package com.pkpm.fastdfs.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *
 */
@Component
@Aspect
public class LogRecordConfig {

    private static final Logger log = LoggerFactory.getLogger(LogRecordConfig.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.bg.fastdfs.rest.*.*(..))")
    public void log() {
    }


    @Before("log()")
    public void before(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("{request url : " + request.getRequestURL().toString() + ", http_method : " + request.getMethod()
                + ", IP : " + request.getRemoteAddr() + ", class_method : "
                + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ", args : "
                + Arrays.toString(joinPoint.getArgs()) + "}");

    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        log.info("{result : " + ret + " spend time : " + (System.currentTimeMillis() - startTime.get()) + " milliseconds}");
        startTime.remove();
    }
}
