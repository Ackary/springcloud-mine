package com.aking.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author ak
 */
public class AnalizerTestDemo {

    private static void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + " ");
        }
        ts.end();
        ts.close();
    }

    private static void print(Analyzer analyzer) throws Exception {
        String text =
                "! @ # $ % ^ & * ( ) _ + - / * +     > < , ? /(ㄒoㄒ)/~~ ^ % $ #5 4 44 44￥ ￥ ￥》 《 《 { } | |—— —— + ——" +
                        "      Jaguar is an endangered animal. It is said that there are less than 20 jaguars in the world currently, one of which is now living in the national zoo of Peru. \n" +
                        "In order to protect this jaguar, Peruvians singled out a pitch of land in the zoo for it, where there are herds of cattle, sheep and deer for the jaguar to eat.\n" +
                        "Anyone who has visited the zoo praised it to be the \"Heaven of Tiger\". However, strange enough, no one has ever seen the jaguar prey on the cattle and sheep. \n" +
                        "Some people thought that the jaguar felt toolonely， so they collected money and rented a female tiger to accompany it. Nonetheless, it did not make too much sense. \n" +
                        "The jaguar just sometimes went out of its house with its \"girlfriend\" and stayed in the sun for a while before it came back to its house again.\n" +
                        "\"It is normal for the jaguar to be lazy in this environment. Tiger is the king of forest but you simply put some small animals around him. That is why the jaguar shows no interest in going out. \n" +
                        "Why don 't you put two wolves or at least a jackal around him?\" A visitor proposed.\n" +
                        "Others all agreed with him and put five panthers into the jaguar's territory. Since then, the jaguar did not go back to its house any more. \n" +
                        "It either stands on top of the hill roaring or goes down from the hill strolling without sleeping all day long. It did not eat the meat provided by the zoo staff and has totally got back to its nature.\n" +
                        "Actually, this principle does not only apply to animals, but also apply to human beings. Here is another story. There was a rich man who was selecting a husband for his only child among a multitude of pursuers. \n" +
                        "The man led all the pursuers to a river and pointed to the crocodiles, saying, \"Anyone who can swim across the river safe and sound will marry my daughter.\" \n" +
                        "Those pursuers looked at each other and no one dare take the initiative. At that moment, a man plunged into the river bravely and swam at a staggering speed to the other side. \n" +
                        "AII the people applauded for his courage with great sense of admiration. Nevertheless, the man, after landing on the bank, shouted angrily, \"Who pushed me into the river just now?\"\n" +
                        "Maybe the man, after thinking of the whole process and the good consequence he is going to obtain, will feel obliged to the one who pushed him into the river. \n" +
                        "It is fairly common that disadvantages will turn into advantages and misfortunes into fortunes! But many of us cannot manage to realize the significance of our \"rivals\" to our success. \n" +
                        "Generally speaking, many people will see the one who \"pushes him into the river\" as an opponent. However, if you think in a deeper sense, \n" +
                        "you will realize that it is also a blessing and opportunity to have someone like that! It is the one who \"pushes you into the river\" who makes you feel the sense of crisis and stimulates your ambition and desire to strive! \n" +
                        "You will resolve to eliminate all difficulties and progress to another stage of your life!\n" +
                        "In our world , one creature without any rivals is a lifeless creature. If a man lives without rivals, he is bound to be satisfied with the present and will not strive for the better. \n" +
                        "He would hold back before all difficulties and decline in inaction and laziness. Adverse environment tends to cultivate successful people. Therefore, your rivals are not your opponents or those you grudge. \n" +
                        "Instead , they are your good friends! In our lives, we need some rivals to \"push us into the river\", leaving us striving ahead in all difficulties and competitions. \n" +
                        "In our work, we need some rivals to be picky about us and supervise our work with rigorous requirements and standards. \n" +
                        "Due to our rivals, we can bring out our potential to the best; Due to our rivals, we will continuously continuously promote our capabilities when competing with them!";
        TokenStream tokenStream = analyzer.tokenStream("content", text);
        CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.print(attribute.toString() + " ");
        }
    }


    public static void main(String[] args) throws Exception {
        // 自定义停用词
        CharArraySet stopWords = new CharArraySet(0, true);
        // 获取系统默认停用词
        stopWords.addAll(StandardAnalyzer.STOP_WORDS_SET);
        String[] myStopWords = {"while", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        stopWords.addAll(Arrays.asList(myStopWords));

        StandardAnalyzer standardAnalyzer = new StandardAnalyzer(stopWords);
        print(standardAnalyzer);

        String text1 = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String text2 = "you will just miss out on the opportunity to brighten someone’s day with this message.";
        String text3 =
                "Jaguar is an endangered animal. It is said that there are less than 20 jaguars in the world currently, one of which is now living in the national zoo of Peru. \n" +
                        "In order to protect this jaguar, Peruvians singled out a pitch of land in the zoo for it, where there are herds of cattle, sheep and deer for the jaguar to eat.\n" +
                        "Anyone who has visited the zoo praised it to be the \"Heaven of Tiger\". However, strange enough, no one has ever seen the jaguar prey on the cattle and sheep. \n" +
                        "Some people thought that the jaguar felt toolonely， so they collected money and rented a female tiger to accompany it. Nonetheless, it did not make too much sense. \n" +
                        "The jaguar just sometimes went out of its house with its \"girlfriend\" and stayed in the sun for a while before it came back to its house again.\n" +
                        "\"It is normal for the jaguar to be lazy in this environment. Tiger is the king of forest but you simply put some small animals around him. That is why the jaguar shows no interest in going out. \n" +
                        "Why don 't you put two wolves or at least a jackal around him?\" A visitor proposed.\n" +
                        "Others all agreed with him and put five panthers into the jaguar's territory. Since then, the jaguar did not go back to its house any more. \n" +
                        "It either stands on top of the hill roaring or goes down from the hill strolling without sleeping all day long. It did not eat the meat provided by the zoo staff and has totally got back to its nature.\n" +
                        "Actually, this principle does not only apply to animals, but also apply to human beings. Here is another story. There was a rich man who was selecting a husband for his only child among a multitude of pursuers. \n" +
                        "The man led all the pursuers to a river and pointed to the crocodiles, saying, \"Anyone who can swim across the river safe and sound will marry my daughter.\" \n" +
                        "Those pursuers looked at each other and no one dare take the initiative. At that moment, a man plunged into the river bravely and swam at a staggering speed to the other side. \n" +
                        "AII the people applauded for his courage with great sense of admiration. Nevertheless, the man, after landing on the bank, shouted angrily, \"Who pushed me into the river just now?\"\n" +
                        "Maybe the man, after thinking of the whole process and the good consequence he is going to obtain, will feel obliged to the one who pushed him into the river. \n" +
                        "It is fairly common that disadvantages will turn into advantages and misfortunes into fortunes! But many of us cannot manage to realize the significance of our \"rivals\" to our success. \n" +
                        "Generally speaking, many people will see the one who \"pushes him into the river\" as an opponent. However, if you think in a deeper sense, \n" +
                        "you will realize that it is also a blessing and opportunity to have someone like that! It is the one who \"pushes you into the river\" who makes you feel the sense of crisis and stimulates your ambition and desire to strive! \n" +
                        "You will resolve to eliminate all difficulties and progress to another stage of your life!\n" +
                        "In our world , one creature without any rivals is a lifeless creature. If a man lives without rivals, he is bound to be satisfied with the present and will not strive for the better. \n" +
                        "He would hold back before all difficulties and decline in inaction and laziness. Adverse environment tends to cultivate successful people. Therefore, your rivals are not your opponents or those you grudge. \n" +
                        "Instead , they are your good friends! In our lives, we need some rivals to \"push us into the river\", leaving us striving ahead in all difficulties and competitions. \n" +
                        "In our work, we need some rivals to be picky about us and supervise our work with rigorous requirements and standards. \n" +
                        "Due to our rivals, we can bring out our potential to the best; Due to our rivals, we will continuously continuously promote our capabilities when competing with them!";
        try (Analyzer ana = new StandardAnalyzer();) {
            TokenStream ts = ana.tokenStream("coent", text3);
            System.out.println();
            System.out.println("标准分词器，英文分词效果：");
            doToken(ts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
