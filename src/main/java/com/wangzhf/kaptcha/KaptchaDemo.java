package com.wangzhf.kaptcha;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

public class KaptchaDemo {

    public static void main(String[] args) throws FileNotFoundException {
        for (int i=0; i< 10; i++) {
            Producer producer = new DefaultKaptcha();
            Properties p = new Properties();
            // 设置图片边框
            p.setProperty("kaptcha.border", "no");
            // 边框颜色，合法值： r,g,b (and optional alpha) 或者
            // white,black,blue.
            p.setProperty("kaptcha.border.color", "black");

            p.setProperty("kaptcha.image.width", "120");
            p.setProperty("kaptcha.image.height", "32");

            // 默认图片实现类
            p.setProperty("kaptcha.producer.impl", "com.google.code.kaptcha.impl.DefaultKaptcha");

            // 文本实现类
            p.setProperty("kaptcha.textproducer.impl", "com.google.code.kaptcha.text.impl.DefaultTextCreator");
            // 文本集合，验证码值从此集合中获取
            p.setProperty("kaptcha.textproducer.char.string", "2345678abcdegfynmnpwxABCDEGFYNMNPWX");
            // 验证码长度 5
            p.setProperty("kaptcha.textproducer.char.length", "4");
            // 字体 Arial, Courier
            p.setProperty("kaptcha.textproducer.font.names", "Arial, Courier");
            // 字体大小 40px
            p.setProperty("kaptcha.textproducer.font.size", "30");
            // 字体颜色，合法值： r,g,b 或者 white,black,blue.
            p.setProperty("kaptcha.textproducer.font.color", "black");
            // 文字间隔 2
            p.setProperty("kaptcha.textproducer.char.space", "8");
            // 干扰实现类
            p.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
            // 干扰颜色，合法值： r,g,b 或者 white,black,blue.
            p.setProperty("kaptcha.noise.color", "black");
            // 图片样式： 水纹com.google.code.kaptcha.impl.WaterRipple
            //                鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
            //                阴影com.google.code.kaptcha.impl.ShadowGimpy
            p.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
            // 背景实现类
            p.setProperty("kaptcha.background.impl", "com.google.code.kaptcha.impl.DefaultBackground");
            // 背景颜色渐变，开始颜色
            p.setProperty("kaptcha.background.clear.from", "white");
            // 背景颜色渐变，结束颜色
            p.setProperty("kaptcha.background.clear.to", "white");
            // 文字渲染器
            p.setProperty("kaptcha.word.impl", "com.google.code.kaptcha.text.impl.DefaultWordRenderer");
            // session中存放验证码的key键
            p.setProperty("kaptcha.session.key", "KAPTCHA_SESSION_KEY");
            // The date the kaptcha is generated is put into the
            //                HttpSession. This is the key value for that item in the
            //                session.
            p.setProperty("kaptcha.session.date", "KAPTCHA_SESSION_DATE");


            Config config = new Config(p);
            ((DefaultKaptcha) producer).setConfig(config);
            String text = producer.createText();
            BufferedImage bi = producer.createImage(text);
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
