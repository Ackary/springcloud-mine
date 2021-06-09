package com.aking.utils;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ak
 */
public class FilterStopWord {

    /**
     * 停用词表路径
     */
    public static String stop_word_path = "D:/Document/data/stop_word.txt";

    /**
     * 停用词map表
     */
    public static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        map = getMap();
        String sentence = "xxxxxx";
        System.out.println(removeStopWords(sentence));
    }

    /**
     * 根据停用词表，过滤句子中包含的停用词
     *
     * @param oldString：原中文文本
     * @return 去除停用词之后的中文文本
     */
    public static String removeStopWords(String oldString) {
        StringBuilder newString = new StringBuilder();
        // 首先对句子进行分词
        List<Term> terms = ToAnalysis.parse(oldString).getTerms();
        System.out.println(terms);

        // 遍历分词后的每个词，看它是否在map中，若在则过滤，若不在，则保存
        for (Term term : terms) {
            // 拿到词
            String word = term.getName();
            // 判断该词是否在停用词字典内
            if (!map.containsKey(word)) {
                String temp = " " + word;
                newString.append(temp);
            }
        }
        return newString.toString();
    }

    /**
     * 获取停用词的map形式
     */
    public static Map<String, Integer> getMap() throws IOException {
        Map<String, Integer> dic = new HashMap<>(16);
        // 加载字典
        InputStreamReader isr = new InputStreamReader(new FileInputStream(stop_word_path), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            dic.put(line.trim(), 1);
        }
        // 关闭文件
        br.close();
        isr.close();

        return dic;
    }
}
