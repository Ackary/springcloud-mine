package com.aking.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {
        String str = "you will just miss out on the opportunity to brighten someone’s day with this message.";
        List<String> wordList = getOriginalText(str);
        for (String word : wordList) {
            System.out.println(word);
        }
    }

    /**
     * 根据string获取对应原始词性词汇列表
     *
     * @param text
     * @return
     */
    public static List<String> getOriginalText(String text) {
        List<String> wordList = new ArrayList<>();
        Properties properties = new Properties();

        //分词、分句、词性标注和次元信息。
        properties.put("annotators", "tokenize,ssplit,pos,lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> words = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap wordTemp : words) {
            for (CoreLabel token : wordTemp.get(CoreAnnotations.TokensAnnotation.class)) {
                String originalWord = token.get(CoreAnnotations.LemmaAnnotation.class);  // 获取对应上面word的词元信息，即我所需要的词形还原后的单词
                wordList.add(originalWord);
            }
        }
        return wordList;
    }
}

