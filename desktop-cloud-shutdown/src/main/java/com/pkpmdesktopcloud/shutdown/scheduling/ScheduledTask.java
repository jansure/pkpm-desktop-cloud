package com.pkpmcloud.scheduling;

import com.pkpmcloud.constants.SchedulingConst;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xuhe
 * @description 自动定时任务
 * @date 2018/5/9
 */
@Component
@EnableScheduling
public class ScheduledTask {

    @Scheduled(fixedRate = SchedulingConst.TIME_INTERVAL)
    public void shutdown(){
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(now);
    }
}
