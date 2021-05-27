import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/30
 */
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        // opsForValue 操作字符串，类似String
        //redisTemplate.opsForValue();

        // opsForList 操作List
        //redisTemplate.opsForList();

//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.select(0);
//        connection.flushDb();
//        connection.flushAll();

        redisTemplate.opsForValue().set("mykey", "aking");
        redisTemplate.opsForValue().get("mykey");
    }

    @Test
    public void testJieba() {
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        String sentences1 =
                "超文本小说是一种以网络为载体，以超文本技术为支撑的新型文学品类。超文本文学作品在文本内部或文本结尾设置有超文本链接点，提供不同的情节走向供读者在阅读时选择，不同的阅读选择会产生不同的结局，因此也称为多向文本文学。\n" +
                        "超文本文学着眼点在于读者的高度参与、自由发挥与即兴创造，使得“过去由于物质和技术的限制而受到阻扼的人的意志和欲望，如今随着高科技的发展，可以畅通无阻地宣泄出来了。”\n" +
                        "超文本文学的出现，实现了德里达、巴特等后现代主义大师提出的解构主义文本理论。与传统文学相比，超文本文学具有非线性、互动性、开放性、非中心化和未完成等特点，对以纸质印刷文本为媒介的传统文学形成了颠覆性的挑战。\n" +
                        "\n" +
                        "但是，真正给传统文学以颠覆性影响的，还不是超文本文学的存现形式，而是它的非线性文本结构。\n" +
                        "传统文学呈现出一种线性结构，以字、词、句、段、篇章、标题的形式固定下来，而且每一页都编了页码。\n" +
                        "传统文学的情节通常完整连贯，一气到底。即使一些后现代主义作品，淡化情节、消解情节，带有更多的不确定性，为艺术想像提供了广阔的天地，但它们仍属扁平的静态结构，缺乏厚度感和立体的延展性。\n" +
                        "超文本文学超越了个别文本的局限，将众多文本通过关键词的链接互联为一个树状的网络系统。在这个系统中，不同的路径纵横交错，读者可自由选择路径进入文本。超文本文学将传统文学静态的封闭的线性结构转化为富有弹性的开放的网状非线性结构。\n" +
                        "非线性的书写系统代替传统的线性叙事，情节的原因和结果不再是严密的对应关系，文本内部结构松散，语意断裂，但又呈现相互关联和串通的特征。\n" +
                        "超文本文学相对于传统平面印刷作品来说，具有革命性的贡献，可以归纳为三个方面：其一，超文本文学以非线性的书写系统代替传统的线性叙事，情节的原因和结果不再是严密的对应关系，文本内部结构松散，语意断裂，但又呈现相互关联和串通的特征。\n" +
                        "作家可以在文本的任何一个地方打断、撕开，开辟新的叙事路径；也可以在文本的任何地方进行缝补、接续，保持文本叙事上的完整性。其二，从叙事主体上，打破了作家对叙事权的垄断，有限度地将叙事权渡让给读者。\n" +
                        "读者可以有限度地决定情节的发展方向，参与作家的创作活动。第三，传统的文学创作规则被打破。在超文本文学中，任何文学故事的情节发展都是多重选择的，读者可以参与进去，可以选择，文学作品之中文字组织的种种既定的规则受到极大的破坏。\n" +
                        "发展历史编辑\n" +
                        "20世纪90年代初，一些美国小说家开始尝试运用超文本技术为小说创作开拓一条新路，将互联网上的超文本和超链接概念应用于小说创作，进行超文本小说的实验性创作。\n" +
                        "1987年美国计算机协会第一届超文本会议上，麦可·乔伊思发布了他的超文本小说《下午，一个故事》，1990年由东门系统公司以磁盘版的形式发行。《下午，一个故事》在每页底部有多重选择的链接按钮，\n" +
                        "由此实现小说在情节发展过程中的多重路向选择，这种技术的实现，在今天的网络技术中已相当普及和简便，但在当时能将超文本技术运用于文学的创作确实是有一定的创新意义的。\n" +
                        "由此，这部小说成为早期超文本小说的经典之作，被誉为“超文本小说的祖师爷”。从发行载体来说，《下午，一个故事》只是磁盘版的超文本小说，还不能称为“网络化文学”，但是，它为网络超文本小说的产生提供了一个范例。\n" +
                        "此后，美国作家史都尔·摩斯洛坡创作《胜利花园》，这部超文本小说虽然也是由东门系统公司以磁盘版的形式进行商业发行，但在网络上发表有一个简本。《胜利花园》不同于《下午，一个故事》将多重路径统一放在每页的文字之外，\n" +
                        "而是将多重超连接直接穿插于文内，每页字行间都选定几个字句作超文本链接的标志，供读者自由选择直接跳页。\n" +
                        "超文本文学在不长的发展历史中，已经形成了从新旧网络功能转化而来的创作技巧，这些技巧的运用，丰富了文学的表现形式，提升了文学的表现力。如在文本主体之外进行链接、跳转，我们可以称之为“外部链接”，《下午，一个故事》就是采用的外部链接。\n" +
                        "在文本之中选择接点进行链接，我们可以称之为“内部链接”，《胜利花园》采用的就是“内部链接”方式。此外还有其他超文本链接、跳转方式，如随机链接，定时跳转等。\n" +
                        "摩斯洛坡的《漫游网际》是采用定时跳转的超文本小说。读者在阅读小说的过程中，如果不使用浏览品的“停止”功能，小说的页面会在30秒内自动跳转页面，引导读者阅读下去。\n" +
                        "1999年，摩斯洛坡在他的新作《雷根图书馆》中，再次尝试了随机跳转技术在文学中的运用。《雷根图书馆》内的超连接对象并不是一一对应的，而是一个页面与多个页进行链接，在跳转时，由计算机随机从多个被链接的页面中选定一个跳转，\n" +
                        "这样，便形成一个多向路的叙事。读者在第一次阅读和第二次、第三次阅读，随机的跳转都会形成不同的文本对象，从而产生不同的文本意义。只要读者有兴趣和耐力多篇反复阅读，每一次阅读都会有新的发现和理解。\n" +
                        "随着网络技术的发展，不断出现的新的技术特性，会带给文学更多的表现方式，同时，已有的创作形式也需要不断的求新，以求更完美地表达文学的内质。不可否认，新的文学创作技巧会给人们带来更多阅读困惑。\n" +
                        "文本跳转时的定时跳转，总是让人神经紧绷，心有旁虑，让人产生一种阅读焦虑情绪。如果带给读者阅读焦虑情绪不是作者的创作所要达到的目的，那么就应该视为创作上的缺陷。再如随机跳转，计算机的随机选择只是一个简单的命令和函数，实现起来并不难。\n" +
                        "但计算机的随机是没有任何文本意义的“随机”，如何让每一次随机产生的文本都具有连续的可读性，而不是前言不搭后语的“天书”，也应是这种技巧进行创作时最大的难度。\n" +
                        "为了使随机选择后的文本具有连续可读性，创作者可能在文字上就要玩弄模糊、虚无、两可的技法，但这样做必然导致文本意义表达的丧失。\n" +
                        "在美国，超文本文学已进入大学课堂。1992年前后，美国小说家罗伯特·库佛率先在布朗大学开设超文本小说写作班；Janet Murray教授也在麻省理工学院开设了“交互式和非线性小说”课程。\n" +
                        "网络上也出现了讨论超文本小说的专门网站，如“超地平线”，为读者阅读超文本小说提供向导。一些优秀的超文本文学作品已被制作成光盘版发行，这表明，超文本小说已具有商业价值了";

        String sentences2 =
                "You Have Only One Life.\n" +
                        "There are moments in life when you miss someone so much that you just want to pick them from your dreams and hug them for real! \n" +
                        "Dream what you want to dream;go where you want to go;be what you want to be,because you have only one life and one chance to do all the things you want to do.\n" +
                        "May you have enough happiness to make you sweet,enough trials to make you strong,enough sorrow to keep you human,enough hope to make you happy? \n" +
                        "Always put yourself in others’shoes.If you feel that it hurts you,it probably hurts the other person, too.\n" +
                        "The happiest of people don’t necessarily have the best of everything;they just make the most of everything that comes along their way.\n" +
                        "Happiness lies for those who cry,those who hurt, those who have searched,and those who have tried,for only they can appreciate the importance of people\n" +
                        "who have touched their lives.Love begins with a smile,grows with a kiss and ends with a tear.\n" +
                        "The brightest future will always be based on a forgotten past, you can’t go on well in life until you let go of your past failures and heartaches.\n" +
                        "When you were born,you were crying and everyone around you was smiling.Live your life so that when you die,you're the one who is smiling and everyone around you is crying.\n" +
                        "Please send this message to those people who mean something to you,to those who have touched your life in one way or another,\n" +
                        "to those who make you smile when you really need it,to those that make you see the brighter side of things when you are really down,\n" +
                        "to those who you want to let them know that you appreciate their friendship.And if you don’t, don’t worry,nothing bad will happen to you,\n" +
                        "you will just miss out on the opportunity to brighten someone’s day with this message.";
        System.out.println(jiebaSegmenter.sentenceProcess(sentences2));
    }

}
