package com.aking.designmode;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略模式
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/9
 */
public class ShareFactory {

    @Getter
    @AllArgsConstructor
    enum ShareType {
        /**
         * 单商品
         */
        SINGLE("single", "单商品"),

        /**
         * 多商品
         */
        MULTI("multi", "多商品"),

        /**
         * 下单
         */
        ORDER("order", "下单");

        private final String code;
        private final String desc;
    }

    /**
     * 定义策略缓存
     */
    private static final Map<String, ShareStrategy> SHARE_STRATEGIES = new HashMap<>(16);

    static {
        SHARE_STRATEGIES.put("order", new OrderItemShare());
        SHARE_STRATEGIES.put("single", new SingleItemShare());
        SHARE_STRATEGIES.put("multi", new MultiItemShare());
    }

    /**
     * 获取指定策略
     */
    public static ShareStrategy getShareStrategy(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty");
        }
        return SHARE_STRATEGIES.get(type);
    }

    public static void main(String[] args) {
        String shareType = "order";
        ShareStrategy shareStrategy = ShareFactory.getShareStrategy(shareType);
        shareStrategy.shareAlgorithm("order");
    }

}
