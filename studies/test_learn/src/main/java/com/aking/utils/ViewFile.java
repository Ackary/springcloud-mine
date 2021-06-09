package com.aking.utils;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @author ak
 */
public class ViewFile {
    public static void main(String[] args) throws AWTException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Robot robot = new Robot();
        File file = new File("C:/jieba.txt");
//		获取修改时间
        long lastModifiedTime = file.lastModified();
        while (true) {
//			一秒钟后，再次获取修改时间
            robot.delay(1000);
            long lastModifiedTime2 = file.lastModified();
            if (lastModifiedTime != lastModifiedTime2) {
                System.out.println(file.getName() + "在" + format.format(lastModifiedTime2) + "被修改过");
                lastModifiedTime = lastModifiedTime2;
            }
        }
    }
}

