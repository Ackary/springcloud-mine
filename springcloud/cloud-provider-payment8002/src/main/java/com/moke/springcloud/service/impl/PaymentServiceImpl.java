package com.moke.springcloud.service.impl;

import com.moke.springcloud.dao.PaymentDao;
import com.moke.springcloud.entities.Payment;
import com.moke.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/7
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
