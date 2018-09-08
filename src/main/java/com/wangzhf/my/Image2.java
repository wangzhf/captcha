package com.wangzhf.my;

import com.wangzhf.feiniu.CaptchaGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Image2 {

    static int width = 120;

    static int height = 32;
    private static final int SCALEFACTOR = 400;

    private int cycles = 1;

    private double points;

    private double[] sines;

    private int[] pts;

    public void paintComponent(BufferedImage bi, Graphics g) {
        points = SCALEFACTOR * cycles * 2.5;
        sines = new double[(int) points];
        for (int i = 0; i < points; i++) {
            double radians = (Math.PI / SCALEFACTOR) * i;
            sines[i] = Math.sin(radians);
        }

        int maxWidth = width;
        double hstep = (double) maxWidth / (double) points;
        int maxHeight = (int) (height / 3);
        pts = new int[(int) points];
        for (int i = 0; i < points; i++)
            pts[i] = (int) (sines[i] * maxHeight / 2 * .95 + maxHeight / 2);

        int startY = 10;
        boolean reverseColor = false;
        for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1] + startY;
            int y2 = pts[i] + startY;

            // 判断颜色
            int rgb = bi.getRGB(x1, y1);
            if(rgb == Color.WHITE.getRGB()){
                //System.out.println("true");
                g.setColor(Color.WHITE);
                //System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
            }else{
                //System.out.println("false");
                g.setColor(new Color(rgb));
            }
            g.drawLine(x1, y1, x2, y2);

            if(x1 != x2) {
                reverseColor = true;
            } else {
                reverseColor = false;
            }

            if(reverseColor){
                // System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
                int tempH = 0;
                for(int h = y1; h < height; h++){
                    for (int s = x1; s <= x2; s++ ){
                        if(h != tempH) {
                            int rgb2 = bi.getRGB(s, h);
                            if(rgb2 == Color.WHITE.getRGB()) {
                                g.setColor(Color.BLACK);
                                g.drawLine(s, h, s, h);
                            } else {
                                g.setColor(Color.WHITE);
                                g.drawLine(s, h, s, h);
                            }
                            tempH = h;
                        }
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        CaptchaGenerator g = new CaptchaGenerator();
        for (int i=0; i<10; i++){
            BufferedImage bi = g.g1();
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            // Image image = new Image();
            Image2 image = new Image2();
            image.paintComponent(bi, g2);
            g2.dispose();
            String path = "E:\\temp\\images\\aa\\";
            String fileName = i + ".png";
            String result = path + fileName;
            File file = new File(result);
            OutputStream out = new FileOutputStream(file);
            try {
                ImageIO.write(bi, "png", out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

//    public static void main(String[] args) throws FileNotFoundException {
//        BufferedImage bi = new BufferedImage(120, 55, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g = (Graphics2D) bi.getGraphics();
//        new Image().paintComponent(g);
//        String path = "E:\\temp\\images\\aa\\";
//        String fileName = "1111.png";
//        String result = path + fileName;
//        File file = new File(result);
//        OutputStream out = new FileOutputStream(file);
//        try {
//            ImageIO.write(bi, "png", out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}
