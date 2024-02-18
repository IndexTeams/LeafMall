package cn.kiroe.demo.task.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author Kiro
 * @Date 2024/02/05 11:21
 **/
@Component
@EnableScheduling
@Slf4j
public class MyTask {
    /**
     * 定时任务执行的方式
     * cron表达式 * * * * * *
     * 1.秒
     * 2.分钟
     * 3.小时
     * 4.天
     * 5.月
     * 6.星期几
     * 7.年
     * *： 表示通配
     *
     */
    //@Scheduled(cron = "* * * * * *")// 每秒执行一次
    //@Scheduled(cron = "10 * * * * *")// 每分钟的第10秒执行
    //@Scheduled(cron = "10,20,30 * * * * *")// 每分钟的第10,20,30秒执行
    //@Scheduled(cron = "30-50 * * * * *")// -表示范围，每单位执行一次
    //@Scheduled(cron = "40/10 * * * * *")// / 表示从每分钟第5秒开始，每次间隔10秒
    // 周和日期会冲突，当指定了一个，另一个要为 ?
    // 时间: 每月的5号 de 11-12之间的每分钟执行一次
    @Scheduled(cron = "0 * 11 5 * ?")
    public void task01(){
        log.info("定时任务执行了:test");
    }
}
