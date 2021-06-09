package com.aking.designmode;

/**
 * OrderItemShare
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/9
 */
public class OrderItemShare implements ShareStrategy {

    @Override
    public void shareAlgorithm(String param) {
        System.out.println("当前分享图片是: " + param);
    }

}
