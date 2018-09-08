package com.wangzhf.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImageModel {

    private int width = 120;

    private int height = 32;

    private static final int SCALEFACTOR = 400;

    private int cycles = 1;

    private double points;

    private double[] sines;

    private int[] pts;

    static Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    Random random = new Random();

    public ImageModel() {
    }

    public ImageModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void paintComponent(BufferedImage bi) {
        // 随机生成样式
        // 0: sin  1: cos  3: square
        int modelType = random.nextInt(3);
        if(modelType == 0 || modelType == 1){
             paintSinOrCos(bi, modelType);
        } else {
            paintSquare(bi, modelType);
        }
    }

    private void paintSquare(BufferedImage bi, int modeType){
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                    RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        int step = 8 + random.nextInt(8);

        int leftX1 = 0;
        int leftY1 = random.nextInt(height / 2);
        int leftX2 = 0;
        int leftY2 = leftY1 + step;

        int rightX1 = width;
        int rightY1 = random.nextInt(height / 2);
        int rightX2 = width;
        int rightY2 = rightY1 + step;

        List<Point> topPoints = getLinePoints(leftX1, leftY1, rightX1, rightY1);
        List<Point> bottomPoints = getLinePoints(leftX2, leftY2, rightX2, rightY2);

        int colorType = random.nextInt(2);
        Color topBottomColor = null;
        Color middleColor = null;
        if(colorType == 0){
            // 上下白色，中间着色
            topBottomColor = DEFAULT_BACKGROUND_COLOR;
            middleColor = RandomColor.getRandomColor();
            // g.setColor(middleColor);
            for (Point top : topPoints){
                int x = top.getX();
                int y = top.getY();
                g.drawLine(x, y, x, y);
            }

            for (Point bottom : bottomPoints){
                int x = bottom.getX();
                int y = bottom.getY();
                g.drawLine(x, y, x, y);
            }

            // bottom
            for (int x = 0; x < width; x++){
                int topY1 = getYFromPoint(topPoints, x);
                int topY2 = getYFromPoint(bottomPoints, x);
                for (int topY = topY1; topY < topY2; topY++) {
                    int rgb = bi.getRGB(x, topY);
                    if(rgb != DEFAULT_BACKGROUND_COLOR.getRGB()) {
                        g.setColor(DEFAULT_BACKGROUND_COLOR);
                    }else{
                        g.setColor(middleColor);
                    }
                    g.drawLine(x, topY, x, topY);
                }
            }
        }else{
            // 上下着色，中间白色
            topBottomColor = RandomColor.getRandomColor();
            middleColor = DEFAULT_BACKGROUND_COLOR;

            for (Point top : topPoints){
                int x = top.getX();
                int y = top.getY();
                g.drawLine(x, y, x, y);
                // System.out.println("line x: " + x + ", y: " + y);
            }

            for (Point bottom : bottomPoints){
                int x = bottom.getX();
                int y = bottom.getY();
                g.drawLine(x, y, x, y);
            }

            // top
            for (Point top : topPoints) {
                int x = top.getX();
                int y = top.getY();
                // top
                for (int topY = 0; topY < y; topY ++) {
                    int rgb = bi.getRGB(x, topY);
                    if (rgb == DEFAULT_BACKGROUND_COLOR.getRGB()){
                        g.setColor(topBottomColor);
                    }else{
                        g.setColor(DEFAULT_BACKGROUND_COLOR);
                    }
                    // System.out.println("color x: " + x + ", y: " + topY);
                    g.drawLine(x, topY, x, topY);
                }
            }

            // bottom
            for (Point bottom : bottomPoints){
                int x = bottom.getX();
                int y = bottom.getY();
                for (int bottomY = y; bottomY < height; bottomY++) {
                    int rgb = bi.getRGB(x, bottomY);
                    if (rgb == DEFAULT_BACKGROUND_COLOR.getRGB()){
                        g.setColor(topBottomColor);
                    }else{
                        g.setColor(DEFAULT_BACKGROUND_COLOR);
                    }
                    g.drawLine(x, bottomY, x, bottomY);
                }
            }
        }

        g.dispose();
    }

    private int getYFromPoint(List<Point> points, int x) {
        for(Point p : points) {
            if (x == p.getX()){
                return p.getY();
            }
        }
        return getYFromPoint(points, x + 1);
    }

    private List<Point> getLinePoints(int x1, int y1, int x2, int y2){
        List<Point> list = new ArrayList<Point>();
        if (x1 == x2) {
            // Tangent = NaN
            int from = Math.min(y1, y2);
            int to = Math.max(y1, y2);
            for (int y = from; y <= to; y++) {
                list.add(new Point(x1, y));
            }
        } else {
            double slope = ((double) (y2 - y1)) / ((double) (x2 - x1));
            int step = (x2 > x1) ? 1 : -1;
            for (int x = x1; x != x2; x += step) {
                int y = (int)((x - x1) * slope + y1);
                list.add(new Point(x, y));
            }
        }
        return list;
    }

    static class Point{
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /**
     * 正弦余弦曲线
     * @param bi
     * @param modelType
     */
    private void paintSinOrCos(BufferedImage bi, int modelType){
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // 上着色
        Color topColor = null;
        // 下着色
        Color buttomColor = null;
        int colorType = random.nextInt(2);
        // 上着色
        if(colorType == 0){
            topColor = DEFAULT_BACKGROUND_COLOR;
            buttomColor = RandomColor.getRandomColor();
        }else{
            topColor = RandomColor.getRandomColor();
            buttomColor = DEFAULT_BACKGROUND_COLOR;
        }

        points = SCALEFACTOR * cycles * 2.5;
        sines = new double[(int) points];
        for (int i = 0; i < points; i++) {
            double radians = (Math.PI / SCALEFACTOR) * i;
            if(modelType == 1){
                sines[i] = Math.cos(radians);
            }else{
                sines[i] = Math.sin(radians);
            }
        }

        int maxWidth = width;
        double hstep = (double) maxWidth / (double) points;
        int maxHeight = (int) (height / 3);
        pts = new int[(int) points];
        for (int i = 0; i < points; i++)
            pts[i] = (int) (sines[i] * maxHeight / 2 * .95 + maxHeight / 2);

        int startY = 10;

        for (int i = 1; i < points; i++) {
            boolean reverseColor = false;
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1] + startY;
            int y2 = pts[i] + startY;

            if(colorType == 0) {
                // 判断颜色
                int rgb = bi.getRGB(x1, y1);
                if(rgb == topColor.getRGB()){
                    //System.out.println("true");
                    g.setColor(topColor);
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
                                if(rgb2 == topColor.getRGB()) {
                                    g.setColor(buttomColor);
                                    g.drawLine(s, h, s, h);
                                } else {
                                    g.setColor(topColor);
                                    g.drawLine(s, h, s, h);
                                }
                                tempH = h;
                            }
                        }
                    }
                }
            }else {
                boolean reverseColor2 = false;
                // 判断颜色
                int rgb = bi.getRGB(x1, y1);
                if(rgb == topColor.getRGB()){
                    //System.out.println("true");
                    g.setColor(topColor);
                    //System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
                }else{
                    //System.out.println("false");
                    g.setColor(new Color(rgb));
                }
                g.drawLine(x1, y1, x2, y2);

                if(x1 != x2) {
                    reverseColor2 = true;
                } else {
                    reverseColor2 = false;
                }

                if(reverseColor2){
                    // System.out.println("sin: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
                    int tempH = -1;
                    int lastX = 0;
                    for(int h = 0; h < y1; h++){
                        for (int s = lastX; s <= x2; s++ ){
                            int rgb2 = bi.getRGB(s, h);
                            if(rgb2 == buttomColor.getRGB()) {
                                g.setColor(topColor);
                                g.drawLine(s, h, s, h);
                            } else if (rgb2 == topColor.getRGB()) {

                            } else {
                                g.setColor(buttomColor);
                                g.drawLine(s, h, s, h);
                            }
                            tempH = h;
                            lastX = s;
                        }
                    }
                }
            }
        }
        g.dispose();
    }

}
