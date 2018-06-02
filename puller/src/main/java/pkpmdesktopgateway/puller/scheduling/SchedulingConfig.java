package pkpmdesktopgateway.puller.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pkpmdesktopgateway.puller.PullerMain.PullerBusiness;

import javax.annotation.Resource;

/**
 * @author wangxiulong
 * @ClassName: SchedulingConfig
 * @Description: 定时器启动类
 * @date 2018年4月3日
 */
@Configuration
@EnableScheduling
@Slf4j
public class SchedulingConfig {

    @Resource
    private PullerBusiness pullerBusiness;

    //	@Scheduled(fixedDelay = 1000*60*5) //上一次任务结束后5分钟执行
    @Scheduled(cron = "*/30 * * * * ?") //每30秒执行一次
    public void updateJobStatus() {
        log.info("定时任务启动");

        //执行业务操作
        pullerBusiness.updateJobStatus();
    }
    
}