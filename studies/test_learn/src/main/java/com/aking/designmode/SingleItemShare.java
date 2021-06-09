package com.aking.designmode;

/**
 * SingleItemShare
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/9
 */
public class SingleItemShare implements ShareStrategy {
    @Override
    public void shareAlgorithm(String param) {
        System.out.println("单商品: " + param);
    }
}
