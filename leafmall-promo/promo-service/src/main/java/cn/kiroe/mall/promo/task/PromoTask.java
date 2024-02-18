package cn.kiroe.mall.promo.task;

import cn.kiroe.mall.promo.service.PromoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author Kiro
 * @Date 2024/02/05 14:58
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class PromoTask {
    private final PromoService promoService;
    /**
     * 用于预热的定时任务
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @PostConstruct
    public void importIntoRedisCacheTask(){
        log.info("测试中：删除索引缓存");
        clearRedisCache();
        log.info("清除成功");
        log.info("缓存预热开始");
        promoService.importIntoRedis();
        log.info("缓存预热结束");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void clearRedisCache(){
        log.info("缓存开始清除");
        promoService.clearRedisCache();
        log.info("缓存清除结束");
    }

}
