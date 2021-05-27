package com.aking;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/29
 */
public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.138.130", 6379);
        jedis.auth("666888");

        System.out.println("ping：" + jedis.ping());
        jedis.flushDB();

        jedis.set("collection", "aa");
        System.out.println(jedis.get("collection"));

        jedis.rpush("collection1", "aa", "bb", "cc");
        System.out.println(jedis.rpop("collection1"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "aking");
        String result = jsonObject.toJSONString();

        // 开启事务
        Transaction multi = jedis.multi();
        try {
            multi.set("usr1", result);
            multi.set("usr2", result);
            // 代码抛出异常，事务执行失败
            int i = 1 / 0;
            multi.exec();
        } catch (Exception e) {
            multi.discard(); // 放弃事务
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("usr1"));
            System.out.println(jedis.get("usr2"));
            jedis.close(); // 关闭连接
        }
    }
}
