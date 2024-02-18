package cn.kiroe.mall.log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Arrays;

/**
 * 通过注解 打印log，打印输入,并计算调用时间
 *
 * @Author Kiro
 * @Date 2024/01/23 21:01
 **/
@Aspect
@Slf4j
@AutoConfiguration
public class LogAspect {

    @Pointcut(value = "@annotation(cn.kiroe.mall.log.annotation.Log)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object handleLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取数据
        Object[] args = joinPoint.getArgs();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        // 开始计时
        Long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed(args);
        Long totalTime = System.currentTimeMillis() - startTime;
        log.info("method:{}-input:{}-totalTime:{}ms", declaringTypeName, Arrays.toString(args), totalTime);
        return proceed;
    }


}
