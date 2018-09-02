package com.wangzhf.feiniu;

import com.wangzhf.my.Image;
import com.wangzhf.my.Image2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class G1 {

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

}
