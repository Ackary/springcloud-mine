package com.moke.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 描述
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/11
 */
@Service
public class PaymentService {
    /**
     * 正常访问
     *
     * @param id id
     */
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "，paymentInfo_OK" + id + "，success!!";
    }

    /**
     * 计算异常、超时异常。只要服务不可用，就会降级
     *
     * @param id id
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_Timeout(Integer id) {
        //int age = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "，paymentInfo_Timeout" + id + "，success!!";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler2", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeoutHandler(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "，paymentInfo_TimeoutHandler" + id + "，success!!";
    }

    public String paymentInfo_TimeoutHandler2(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "，系统繁忙！" + id + "，failed!!";
    }

    /**
     * 服务熔断
     */

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否启用断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0)
            throw new RuntimeException("****id 不能为负数");
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id：" + id + "，不能为负数，请稍后再试，。。。";
    }

}
