package com.wangzhf.other;

import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;

public class FileConvert {

    static File file = new File("D:\\temp\\images\\pingan\\0.jpg");

    static File destFile = new File("D:\\temp\\images\\pingan\\000.jpg");

    public static void convert() throws IOException {
        BufferedImage bi = new BufferedImage(70, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        BufferedImage im = ImageIO.read(file);
        g2.setColor(Color.WHITE);
        g2.drawImage(im, 0, 0, 70, 40, null);
        g2.dispose();

        ImageIO.write(bi, "jpg", new FileOutputStream(destFile));
    }

    public static void main(String[] args) throws IOException {
        convert();
    }
}
