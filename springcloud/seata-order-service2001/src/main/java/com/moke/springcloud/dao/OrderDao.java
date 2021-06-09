package com.moke.springcloud.dao;

import com.moke.springcloud.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/25
 */
@Mapper
public interface OrderDao {
    void create(Order order);

    //修改订单状态，从0改为1
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
