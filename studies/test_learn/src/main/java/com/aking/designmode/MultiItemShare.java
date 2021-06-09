package com.aking.designmode;

/**
 * MultiItemShare
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/9
 */
public class MultiItemShare implements ShareStrategy {
    @Override
    public void shareAlgorithm(String param) {
        System.out.println("多商品: " + param);
    }
}
