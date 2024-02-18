package cn.kiroe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author Kiro
 * @Date 2024/01/17 22:56
 **/
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "cn.kiroe")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }
}
