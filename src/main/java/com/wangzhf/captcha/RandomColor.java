package com.wangzhf.captcha;

import java.awt.*;
import java.util.Random;

/**
 * 获取随机颜色
 */
public class RandomColor {

    public static Color[] colors = {
            new Color(116, 33, 53),
            new Color(49, 43, 131),
            new Color(101, 94, 13),
            new Color(77, 122, 93),
            new Color(66, 37, 93),
            new Color(81, 118, 38),
            new Color(33, 117, 81),
            new Color(25, 81, 78),
            new Color(23, 27, 72),
            new Color(24, 52, 126),
            new Color(122, 89, 126),
            new Color(46, 87, 79),
            new Color(23, 47, 83),
            new Color(103, 39, 99),
            new Color(71, 74, 79),
            new Color(63, 38, 59),
            new Color(100, 113, 23),
            new Color(46, 126, 123)
    };

    static Random random = new Random();

    /**
     * 随机返回默认颜色中的一种
     * @return
     */
    public static Color getRandomColor(){
        return colors[random.nextInt(colors.length)];
    }

    public static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    public static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
