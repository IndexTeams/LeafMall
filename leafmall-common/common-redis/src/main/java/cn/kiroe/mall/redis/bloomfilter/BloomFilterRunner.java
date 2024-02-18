package cn.kiroe.mall.redis.bloomfilter;

import cn.kiroe.mall.common.constant.RedisConst;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @Author Kiro
 * @Date 2024/01/24 10:14
 **/

/**
 * 布隆过滤器的使用
 * 1. 初始化
 * 在项目启动时，初始化
 * a : 写在main方法中，一般不使用，不优雅，不好维护
 * b : 使用Servlet提供的注解 @PostConstruct
 * c : 使用由Spring提供的接口 (CommandLineRunner| ApplicationRunner) 我们只需要实现这两个接口
 * <p>
 * 2. 添加元素
 * 在上架商品的时候添加
 * 通过布隆过滤查看是否有这个数据
 */
@Component
@Slf4j
public class BloomFilterRunner implements CommandLineRunner {
    @Autowired
    RedissonClient redissonClient;

    @Override
    public void run(final String... args) {
        long expectedInsertion = 1000;
        double falseProbability = 0.01;
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
        // 如果存在则不会初始化，返回false
        boolean isInit = bloomFilter.tryInit(expectedInsertion, falseProbability);
        if(isInit){
            log.info("布隆过滤器初始化成功，expectedInsertion:{},falseProbability:{}", expectedInsertion, falseProbability);
        }else {
            log.info("布隆过滤器已存在,expectedInsertion:{},falseProbability:{}",bloomFilter.getExpectedInsertions(),bloomFilter.getFalseProbability());
        }

    }
}
