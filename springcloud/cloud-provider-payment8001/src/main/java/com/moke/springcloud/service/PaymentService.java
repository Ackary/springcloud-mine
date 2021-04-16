package com.moke.springcloud.service;

import com.moke.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * 描述
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/7
 */
public interface PaymentService {
    /**
     * 新增Payment
     *
     * @param payment payment实体参数
     * @return 成功1，失败0
     */
    int create(Payment payment);

    /**
     * 根据id获取payment
     *
     * @param id payment id
     * @return payment实体
     */
    Payment getPaymentById(@Param("id") Long id);
}
