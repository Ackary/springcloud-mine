package com.aking.utils;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/5/25
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class WordCount {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        //用HashMap存放<单词:词频>这样一个映射关系
        HashMap<String, Integer> hashMap = new HashMap<>(32);
        //用正则表达式来过滤字符串中的所有标点符号
        String regex = "[【】、.。,\"!--;:?\'\\]]";

        try {
            //读取要处理的文件
            BufferedReader br = new BufferedReader(new FileReader("C:/jieba.txt"));
            String value;
            while ((value = br.readLine()) != null) {
                value = value.replaceAll(regex, " ");
                //使用StringTokenizer来分词(StringTokenizer详见JDK文档)
                StringTokenizer tokenizer = new StringTokenizer(value);
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken();
                    if (!hashMap.containsKey(word)) {
                        hashMap.put(word, 1);
                    } else {
                        int k = hashMap.get(word) + 1;
                        hashMap.put(word, k);
                    }
                }
            }

            int count = 0;
            //遍历HashMap,输出结果
            for (String word : hashMap.keySet()) {
                System.out.println(word + ":\t" + hashMap.get(word));
                count += hashMap.get(word);
            }
            System.out.println("count: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("耗时: " + time + "ms");
    }

}
