package com.moke.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/13
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "------PaymentFallbackService fall back paymentInfo_OK";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "------PaymentFallbackService fall back paymentInfo_Timeout";
    }
}
