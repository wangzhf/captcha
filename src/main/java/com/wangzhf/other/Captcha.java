package com.wangzhf.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * 图片验证码类
 *
 */
public class Captcha {
	
	private static final String CHARACTORS = "23456789abcdefghjkmnprstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
	private Random random = new Random();
	
	private int length = 5;
	private int width = 200;
	private int height = 80;
	private String fontName = "Arial";
	private int INTERFERINGLINE_COUNT = 100;
	private float NOISE_RATE = 0.25f;
	
	/**
	 * 无参构造函数, 默认验证码长度=5, 生成的图片宽=200, 高=80, 字体=Arial
	 */
	public Captcha(){
	}
	
	/**
	 * 构造函数
	 * @param length 验证码长度
	 */
	public Captcha(int length){
		this.length = length;
	}
	
	/**
	 * 构造函数
	 * @param length 验证码长度
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public Captcha(int length, int width, int height){
		this.length = length;
		this.width = width;
		this.height = height;
	}	
	
    /**
     * 构造函数
     * @param length 验证码长度
     * @param width 图片宽
     * @param height 图片高
     * @param fontName 字体名
     */
	public Captcha(int length, int width, int height, String fontName){
		this.length = length;
		this.width = width;
		this.height = height;
		this.fontName = fontName;
	}

	/**
	 * 使用指定源生成验证码
	 * @return 验证码
	 */
	public String generateChars(){
		int codesLen = CHARACTORS.length();
		StringBuilder chars = new StringBuilder(length);
		for(int i = 0; i < length; i++){
			chars.append(CHARACTORS.charAt(random.nextInt(codesLen-1)));
		}
		return chars.toString();
	}
	
	/**
	 * 生成随机验证码, 返回其图片的Base64加密字符串
	 * @return	String 生成随机验证码, 返回其图片的Base64加密字符串
	 * @throws IOException 异常信息
	 */
	public String outputImage() throws IOException{
		String chars = generateChars();
		return outputImage(chars);
	}

	public static void main(String[] args) throws IOException {
		for (int i=0; i< 10; i++) {
			Captcha c = new Captcha(4);
			String chars = c.generateChars();
			BufferedImage bi = c.getImage(chars);
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
	
	/**
	 * 输出指定验证码的图片的Base64加密字符串
	 * @param chars 指定验证码
	 * @throws IOException 异常
	 */
	public String outputImage(String chars) throws IOException{
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color c = getRandColor(200, 250);
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		drawInterferingLine(image);
		shear(g, c);
		drawNoisePoints(image);
		drawChars(image, chars);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
        byte[] bytes = outputStream.toByteArray();
        return "";
	}

	public BufferedImage getImage(String chars) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		Color c = getRandColor(200, 250);
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		drawInterferingLine(image);
		shear(g, c);
		drawNoisePoints(image);
		drawChars(image, chars);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		byte[] bytes = outputStream.toByteArray();
		return image;
	}
	
	private void drawChars(BufferedImage image, String str){
		Graphics2D g = (Graphics2D)image.getGraphics();
		int size = str.length();
		g.setColor(getRandColor(100, 160));
		Font font = new Font(fontName, Font.ITALIC, (int)(height*0.8));
		g.setFont(font);
		char[] chars = str.toCharArray();
		for(int i = 0; i < size; i++){
			AffineTransform affine = new AffineTransform();
			double delta = 0.0d;
			if(i == 0){
				delta = Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : 0);
			}else if(i == size-1){
				delta = Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 0 : -1);
			}else{
				delta = Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1);
			}
			affine.setToRotation(delta,(width / size) * i + size/2, (int)(height*0.8));
			g.setTransform(affine);
			g.drawChars(chars, i, 1, (width/size) * i, (int)(height*0.8));
		}
		
		g.dispose();
	}

	/**
	 * 绘制干扰点
	 * @param image
	 */
	private void drawNoisePoints(BufferedImage image){
		int area = (int) (NOISE_RATE * width * height);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}		
	}
	
	/**
	 * 干扰线
	 * @param image
	 */
	private void drawInterferingLine(BufferedImage image){
		Graphics g = image.getGraphics();
		for (int i = 0; i < INTERFERINGLINE_COUNT; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(10) + 1;
			int yl = random.nextInt(10) + 1;
			g.setColor(getRandColor(80, 100));
			g.drawLine(x, y, x + xl + 8, y + yl + 8);
		}
	}
	
	/**
	 * 随机颜色
	 * @param fc frontColor
	 * @param bc backgroundColor
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	private int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}
	
	private int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	private void shear(Graphics g, Color color) {
		shearX(g, width, height, color);
		shearY(g, width, height, color);
	}
	
	private void shearX(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(100);
		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period )
					* Math.sin((double) i / (double) period
							+ (Math.PI * 2 * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}
	}

	private void shearY(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(40) + 10; 
		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (Math.PI * 2 * (double) phase)
							/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}
	}
}
