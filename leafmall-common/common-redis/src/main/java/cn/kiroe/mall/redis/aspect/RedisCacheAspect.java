package cn.kiroe.mall.redis.aspect;

import cn.hutool.core.util.StrUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.redis.annotation.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Kiro
 * @Date 2024/01/23 16:32
 **/
@Aspect
@Component
public class RedisCacheAspect {
    @Autowired
    RedissonClient redissonClient;

    @Around("@annotation(cn.kiroe.mall.redis.annotation.RedisCache)")
    public Object redisCacheAspect(ProceedingJoinPoint joinPoint) {
        // 0. 获取 方法信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取方法的返回值类型
        Class returnType = methodSignature.getReturnType();
        // 获取方法上的注解对象
        RedisCache annotation = methodSignature.getMethod().getAnnotation(RedisCache.class);
        // 1. 从Redis中查询数据
        // 首先设置key
        String prefix = annotation.prefix();
        String key = StrUtil.format("{}:{}", prefix, Arrays.toString(args));
        // 2. 查询到直接返回
        RBucket<Object> bucket = redissonClient.getBucket(key);
        Object object = bucket.get();
        if (object != null) {
            return object;
        }
        // 3. 如果没有查询到
        // 4. 加锁 防止穿透
        String lockKey = key + ":lock";
        RLock lock = redissonClient.getLock(lockKey);
        // 5. 执行方法，double check 增加效率，防止多次差数据库
        try {
            lock.lock(); // 加锁防止 击穿
            object = bucket.get();
            if (object != null) {
                return object;
            }
            // 执行方法
            Object proceedObject = joinPoint.proceed(args);
            // 6. 储存值redis
            // 防止穿透
            if (proceedObject == null) {
                if (Map.class.equals(returnType)) {
                    proceedObject = new HashMap<>();
                } else if (List.class.equals(returnType)) {
                    proceedObject = new ArrayList<>();
                } else {
                    // 其他类型
                    Constructor declaredConstructor = returnType.getDeclaredConstructor();
                    declaredConstructor.setAccessible(true);
                    proceedObject = declaredConstructor.newInstance();
                }
                // 设置短时间
                bucket.set(proceedObject, RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
            } else {
                // 设置长时间
                bucket.set(proceedObject, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
            }
            return proceedObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
