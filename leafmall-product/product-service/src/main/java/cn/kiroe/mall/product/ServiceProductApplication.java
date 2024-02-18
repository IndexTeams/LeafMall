package cn.kiroe.mall.product;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "cn.kiroe.mall")
@EnableFeignClients
@EnableAspectJAutoProxy
@MapperScan("cn.kiroe.mall.product.mapper")
public class ServiceProductApplication{

    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class, args);
    }


}
