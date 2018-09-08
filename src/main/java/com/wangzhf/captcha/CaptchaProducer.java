package com.wangzhf.captcha;

import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

public class CaptchaProducer {

    // Verdana, Serif, Sanserif, Monospace, Dialog, Verdana
    static String FONT_NAME = "";

    static int WIDTH = 120;

    static int HEIGHT = 32;

    static int FONT_SIZE = 24;

    public static String VERIFY_CODES = "23456789abcdefghjkmnprstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    private static Random random = new Random();

    public static BufferedImage getImage(int w, int h, String code)
            throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        Color c = RandomColor.getRandColor(200, 250);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);

        g2.setColor(RandomColor.getRandColor(100, 160));
        FONT_SIZE = h - 4;
        Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            double theta = Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1);
            double anchorx = (w / verifySize) * i + FONT_SIZE / 2 ;
            double anchory = h / 2 ;
            // 纠正倾斜度
            if(theta < -0.3){
                theta = -0.3;
            }
            if(theta > 0.3) {
                theta = 0.3;
            }
            // System.out.println(new String(chars) + ", theta: " + theta + ", x: " + anchorx + ", y: " + anchory);
            affine.setToRotation(theta, anchorx, anchory);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2
                    + FONT_SIZE / 2 - 5);
        }
        g2.dispose();
        return image;
    }

    public static BufferedImage getNormalImage(int w, int h, String code)
            throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);
        g2.setColor(Color.BLACK);// 设置边框色
        // g2.fillRect(0, 0, w, h);
        g2.drawRect(0, 0, w - 1, h - 1);

        Color c = RandomColor.getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.setColor(Color.WHITE);
        g2.fillRect(1, 1, w - 2, h - 2);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(RandomColor.getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(10) + 1;
            int yl = random.nextInt(10) + 1;
            // g2.setColor(getRandColor(80, 100));
            g2.drawLine(x, y, x + xl + 8, y + yl + 8);
        }

        int fontSize = FONT_SIZE;
        Font font = new Font("YaHei Consolas Hybrid", Font.BOLD, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        FontMetrics fm = FontDesignMetrics.getMetrics(font);
        int start = 5;
        int perWid = w / verifySize;
        for (int i = 0; i < verifySize; i++) {
            g2.setColor(RandomColor.getRandColor(80, 180));
            char a = code.charAt(i);
            int charWid = fm.charWidth(a);
            int temp = 0;
            if(charWid < perWid) {
                temp = (perWid - charWid) / 2;
            }
            // System.out.println("perwid: " + perWid + ", charwid: " + charWid);
            g2.drawString(a + "", perWid * i + temp, 26);

        }
        g2.dispose();
        return image;
    }


    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize
     *            验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }


    /**
     * 使用指定源生成验证码
     *
     * @param verifySize
     *            验证码长度
     * @param sources
     *            验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }
}
