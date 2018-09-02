package com.wangzhf.my;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Image2 {

    static int width = 120;

    static int height = 32;
    private static final int SCALEFACTOR = 200;

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
        for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1] + startY;
            int y2 = pts[i] + startY;

            // 判断颜色
            int rgb = bi.getRGB(x1, y1);
            if(rgb == Color.WHITE.getRGB()){
                //System.out.println("true");
                g.setColor(Color.RED);
                //System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
            }else{
                //System.out.println("false");
                g.setColor(new Color(rgb));
            }
            g.drawLine(x1, y1, x2, y2);
            System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);

            // 下方设置颜色
            int tempHeight = height - y2;
            if(tempHeight < 0){
                height = y2;
            }

            for (int m = y1 + 1; m < height ; m++){
                int rgb2 = bi.getRGB(x1, m);
                System.out.println("x1: " + x1 + ", m: " + m + ", rgb2: " + rgb2);
                System.out.println(m);
                if(rgb2 == Color.WHITE.getRGB()){
                    g.setColor(Color.RED);
                    System.out.println("111");
                    // g.drawLine(x1, y1, x1, height * 2);
                    g.drawLine(x1, y1, x1, m);
                }else{
                    System.out.println("222");
                    g.setColor(new Color(rgb2));
                    g.drawLine(x1, y1, x1, m);
                }
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
