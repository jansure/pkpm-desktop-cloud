package com.messageserver.messageserver.service.scheduling;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.messageserver.messageserver.service.message.MessageService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class MessageScheduling {

    @Resource
    private MessageService messageService;

    @Scheduled(cron = "*/5 * * * * ?") //每5秒执行一次
    public void messageTask() {
        log.info("定时任务启动");

        //执行
        messageService.messageTask();
    }
}