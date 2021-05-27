package com.aking.utils;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ak
 */
public class FilterStopWord {

    //停用词表路径
    public static String stop_word_path = "D:/Document/data/stop_word.txt";
    public static Map<String, Integer> map; //停用词map表

    public static void main(String[] args) throws IOException {
        map = getMap();
        String sentence = "xxxxxx";
        System.out.println(RemoveStopWords(sentence));
    }

    /**
     * 根据停用词表，过滤句子中包含的停用词
     *
     * @param oldString：原中文文本
     * @return 去除停用词之后的中文文本
     * @throws IOException
     */
    public static String RemoveStopWords(String oldString) throws IOException {
        String newString = "";
        // 首先对句子进行分词
        List<Term> terms = ToAnalysis.parse(oldString).getTerms();
        System.out.println(terms);

        // 遍历分词后的每个词，看它是否在map中，若在则过滤，若不在，则保存
        for (int i = 0; i < terms.size(); i++) {
            String word = terms.get(i).getName(); // 拿到词
            if (!map.containsKey(word)) { // 判断该词是否在停用词字典内
                String temp = " " + word;
                newString += temp;
            }
        }
        return newString;
    }

    /**
     * 获取停用词的map形式
     *
     * @return
     * @throws IOException
     */
    public static Map<String, Integer> getMap() throws IOException {
        Map<String, Integer> Dic = new HashMap<String, Integer>();
        // 加载字典
        InputStreamReader isr = new InputStreamReader(new FileInputStream(stop_word_path), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            Dic.put(line.trim(), 1);
        }
        // 关闭文件
        br.close();
        isr.close();

        return Dic;
    }
}
