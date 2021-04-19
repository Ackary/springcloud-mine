package com.moke.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/18
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelApplicationMain8401 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplicationMain8401.class, args);
    }
}
