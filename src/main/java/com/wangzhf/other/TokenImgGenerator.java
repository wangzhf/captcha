package com.wangzhf.other;

import com.wangzhf.feiniu.CaptchaConfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import javax.imageio.ImageIO;


public class TokenImgGenerator {
	
	private int width;//图片宽度
	private int height;//图片高度
	
	public TokenImgGenerator(){
		width= 120;
		height = 34;
	}
	
	private String[] fontFamily = new String[]{"Comic San MS"};
	private int[] fontStyle= new int[]{Font.BOLD, Font.BOLD+Font.ITALIC };
	private int[] fontSize = new int[]{20,22,24,25};
	
	/**
	 * 根据token产生验证码图片，结果保存到流
	 * @param token
	 * @throws IOException 
	 */
	public BufferedImage createImage(String token ) throws IOException{
		//随机数对象
		Random random = new Random();
		//正弦曲线
		int base = height / 2 + 8;
		int[] sinCoordinates = new int[]{base, base-6, base,base+6, base, base-4, base, base+6};
		
		//随机字体
		int tokenLength = token.length();
		Font[] choosedFonts = new Font[tokenLength];
		for( int i = 0; i < tokenLength; i++ ){
			String choosedFontFamily = fontFamily[random.nextInt(fontFamily.length)];
			int choosedFontStyle = fontStyle[random.nextInt(fontStyle.length)];
			int choosedFontSize = fontSize[random.nextInt(fontSize.length)];
			Font f = new Font(choosedFontFamily, choosedFontStyle, choosedFontSize);
			choosedFonts[i] = f;
		}
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		//绘制背景
		g.setColor(new Color(217, 217, 255));
        g.fillRect(0, 0, width, height);
        //绘制字符
        for(int k = 0; k < tokenLength; k++){
            String s1 = String.valueOf(token.charAt(k));
            g.setFont(choosedFonts[k]);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(s1, 15 * k + 16, sinCoordinates[k]);
        }
        //加干扰
        g.setFont(new Font("Comic San MS", 0, 21));
        g.setColor(getRandomColor(160, 200));
        for(int j = 0; j < 20; j++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int w = random.nextInt(12);
            int h = random.nextInt(12);
            g.drawOval(x, j, x + w, y + h);
        }
        
        g.dispose();
		return bufferedImage;
	}

	public static void main(String[] args) throws IOException {
		CaptchaConfig captchaConfig = new CaptchaConfig();
		Random random = new Random();
		for (int i=0; i<10; i++){
			String captchaCharStr = captchaConfig.getCharacters();
            char[] captchaChars = captchaCharStr.toCharArray();
            String captcha = "";
            for (int j = 0, length = 4; j < length; j++) {
                captcha += captchaChars[random.nextInt(captchaChars.length)];
            }
			BufferedImage bi = new TokenImgGenerator().createImage(captcha);
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
	
	private Color getRandomColor(int start, int end) {
		Random random = new Random();
		if (start > 255)
			start = 255;
		if (end > 255)
			end = 255;

		int r = start + random.nextInt(end - start);
		int g = start + random.nextInt(end - start);
		int b = start + random.nextInt(end - start);
		return new Color(r, g, b);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}