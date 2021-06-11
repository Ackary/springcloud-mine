package com.aking.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;

import java.util.Iterator;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/10
 */
public class HutoolTokenizerUtil {
    public static void main(String[] args) {
        //自动根据用户引入的分词库的jar来自动选择使用的引擎
        TokenizerEngine engine = TokenizerUtil.createEngine();
        //解析文本
        String text = "微信（WeChat）是腾讯公司于2011年1月21日推出的一款面向智能终端的即时通讯软件，由张小龙带领腾讯广州研发中心产品团队打造";
        Result result = engine.parse(text);
        //输出：这 两个 方法 的 区别 在于 返回 值
        String resultStr = CollUtil.join((Iterator<Word>) result, " ");
        System.out.println(resultStr);
    }
}
