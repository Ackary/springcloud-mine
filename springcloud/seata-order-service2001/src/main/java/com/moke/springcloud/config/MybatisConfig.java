package com.moke.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.moke.springcloud.dao")
public class MybatisConfig {

}
