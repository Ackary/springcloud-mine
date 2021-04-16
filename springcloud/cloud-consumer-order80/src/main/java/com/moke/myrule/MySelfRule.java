package com.moke.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/9
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule getMyRule() {
        return new RandomRule();//随机
    }
}
