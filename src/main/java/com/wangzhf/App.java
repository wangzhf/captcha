package com.wangzhf;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        int x1 = 20, y1 = 50, x2 = 300, y2 = 600;
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
        System.out.println(list);
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


}
