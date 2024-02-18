package cn.kiroe.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @Author Kiro
 * @Date 2024/01/23 14:43
 **/
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    RedissonClient redissonClient;

    public void testRedisson() {
        log.info(redissonClient.toString());
        RKeys keys = redissonClient.getKeys();
        log.info(keys.randomKey());
    }

    /**
     * 给num+1
     * 使用redisson实现的 分布式
     */
    void incrWithRedissonLock() {
        RLock lock = redissonClient.getLock("lock:num");
        try {
            // 1. 加锁
            lock.lock(10, TimeUnit.MINUTES);
        } finally {
            // 2. 执行业务
            lock.unlock();
        }
    }


}
