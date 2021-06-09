package com.aking.moke;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/27
 */
@SpringBootApplication
public class SaTokenStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaTokenStartApplication.class, args);
        System.out.println("启动成功：sa-token配置如下：" + SaManager.getConfig());
    }
}
