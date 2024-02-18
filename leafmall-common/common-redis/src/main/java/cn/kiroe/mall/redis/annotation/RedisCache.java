package cn.kiroe.mall.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Kiro
 * @Date 2024/01/23 16:30
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Documented // 表示会给这个注解生成jdk文档
//@Inherited // 表示这个注解是否能被继承
public @interface RedisCache {
    // 定义属性
    String prefix();

}
