package com.moke.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/18
 */
@RestController
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "testB";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey") // value值可任意，只要唯一
    // 即方法testHotKey里面第一个参数只要QPS超过每秒一次，马上降级处理 (配置Sentinel控制台配置规则)
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        int age = 10 / 0; // 只处理Sentinel控制台添加的违规项，此异常不被"blockHandler"处理
        return "------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "------deal_testHotKey,/(ㄒoㄒ)/~~"; // Sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }
}
