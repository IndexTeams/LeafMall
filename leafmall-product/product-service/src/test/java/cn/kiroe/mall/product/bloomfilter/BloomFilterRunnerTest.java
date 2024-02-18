package cn.kiroe.mall.product.bloomfilter;

import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.mapper.SkuInfoMapper;
import cn.kiroe.mall.product.model.SkuInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/24 10:51
 **/
@SpringBootTest
class BloomFilterRunnerTest {

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    RedissonClient redissonClient;
    @Ignore
    void initBloomFilter(){
        List<Long> list = skuInfoMapper.selectList(new LambdaQueryWrapper<SkuInfo>().select(SkuInfo::getId)).stream().map(SkuInfo::getId).toList();
        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
        bloomFilter.tryInit(1000,0.01);
        long add = bloomFilter.add(list);
    }

    void testInit(){

    }

}