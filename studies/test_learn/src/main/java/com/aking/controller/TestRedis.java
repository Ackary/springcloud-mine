package com.aking.controller;

import com.aking.pojo.User;
import com.aking.utils.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TestRedis
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/30
 */
@RestController
public class TestRedis {
    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    @GetMapping("/redis")
    public void testRedis() {
        // opsForValue操作字符串
        redisTemplate.opsForValue().set("mykey", "艾奎");
        System.out.println(redisTemplate.opsForValue().get("mykey"));

        // 获取redis的连接对象
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        connection.flushAll();
    }

    @GetMapping("serial")
    public void testSerial() throws JsonProcessingException {
        // 真实的开发一般都使用json来传递对象
        User user = new User("艾克", 22);
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        //redisTemplate.opsForValue().set("user", jsonUser);


        // 直接传递对象会报错，因为没有序列化
        // 另一种方案是对User实现序列化
        redisTemplate.opsForValue().set("user", user);

        System.out.println(redisTemplate.opsForValue().get("user"));

        RedisUtil.getInstance().getExpire("aa");
    }
}
