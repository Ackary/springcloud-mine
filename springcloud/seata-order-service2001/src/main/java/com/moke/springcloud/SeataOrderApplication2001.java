package com.moke.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient

/**
 * 使用自己配置的seata对数据源进行代理
 * 因为seata要操作数据库事务，所以要将数据源交给seata管理
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class SeataOrderApplication2001 {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication2001.class,args);
    }
}
