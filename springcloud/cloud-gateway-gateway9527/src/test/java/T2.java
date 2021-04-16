import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/4/14
 */
public class T2 {
    @Test
    public void test() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();//默认时区
        System.out.println(zonedDateTime);
    }
}
