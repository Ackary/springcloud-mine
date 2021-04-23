package com.moke.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.moke.springcloud.entities.CommonResult;
import com.moke.springcloud.entities.Payment;
import com.moke.springcloud.myhandler.CustomBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/22
 */
@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult<?> byResource() {
        return new CommonResult<>(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult<?> handleException(BlockException exception) {
        return new CommonResult<>(444, exception.getClass().getCanonicalName() + "，服务不可用");
    }

    @GetMapping("/rateLimit/byUrl") //没有兜底方法，使用系统默认
    @SentinelResource(value = "byUrl")
    public CommonResult<?> byUrl() {
        return new CommonResult<>(200, "按url限流测试OK", new Payment(2020L, "serial002"));
    }

    @GetMapping("/rateLimit/customBlockHandler") //没有兜底方法，使用系统默认(前提是在sentinel控制台配置后)
    @SentinelResource(value = "customBlockHandler", blockHandlerClass = CustomBlockHandler.class, blockHandler = "handlerException")
    public CommonResult<?> customBlockHandler() {
        return new CommonResult<>(200, "按自定义限流测试OK", new Payment(2020L, "serial003"));
    }
}
