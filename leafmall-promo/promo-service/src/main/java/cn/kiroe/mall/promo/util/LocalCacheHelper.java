package cn.kiroe.mall.promo.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建日期: 2023/03/13 11:24
 *
 * @author ciggar
 *
 * 本地缓存类
 */
@Slf4j
@Component
public class LocalCacheHelper {
    /**
     * 为了方便调试 换成redis,以防每次都要，设置localCache的值
     */

    /**
     * 缓存容器
     */
    private static Map<String, Object> cacheMap;
    static {
        RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);
        cacheMap = redissonClient.getMap(RedisConst.LOCAL_CACHE_HELPER);
    }

    /**
     * 加入缓存
     *
     * @param key
     * @param cacheObject
     */
    public static void put(String key, Object cacheObject) {
        cacheMap.put(key, cacheObject);
        log.info("当前本地缓存 cacheMap:{}", JSON.toJSONString(cacheMap));
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static Object get(String key) {
        return cacheMap.get(key);
    }

    /**
     * 清除缓存
     *
     * @param key
     * @return
     */
    public static void remove(String key) {
        cacheMap.remove(key);
    }


    public static synchronized void removeAll() {
        cacheMap.clear();
    }
}

