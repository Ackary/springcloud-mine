package com.moke.springcloud.controller;

import com.moke.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/12
 */
@RestController
@Slf4j
//配置全局的降级处理，单个的也可单独配置(全局fallback优先级 > 通配服务降级)
@DefaultProperties(defaultFallback = "paymentInfo_Global_FallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_OK(id);
    }

    //    @GetMapping(value = "/consumer/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000")
//    })
    //未指明，使用default降级(每个方法配一个，膨胀)
    @HystrixCommand
    @GetMapping(value = "/consumer/hystrix/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable("id") Integer id) {
        int age = 10 / 0;
        return paymentHystrixService.paymentInfo_Timeout(id);
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "支付系统繁忙！！";
    }

    //全局降级方法(和业务逻辑混在一起，混乱)
    public String paymentInfo_Global_FallbackMethod() {
        return "Global异常处理信息，请稍后再试，。。。";
    }

}
