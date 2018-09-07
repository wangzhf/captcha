package com.wangzhf.feiniu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class CaptchaGenerator {
	
	private CaptchaConfig captchaConfig;
	
	private Random random = new Random();
	
	public CaptchaGenerator(){
		captchaConfig = new CaptchaConfig();
	}
	
	public CaptchaGenerator(CaptchaConfig captchaConfig){
		this.captchaConfig = captchaConfig;
	}
	
	public String create(ByteArrayOutputStream os) {
        String captcha = "";
        String captchaCharStr = captchaConfig.getCharacters();
        char[] captchaChars = captchaCharStr.toCharArray();
        for (int i = 0, length = 4; i < length; i++) {
            captcha += captchaChars[random.nextInt(captchaChars.length)];
        }
 
        //BufferedImage bi = new BufferedImage(120, 55, BufferedImage.TYPE_INT_ARGB);
        BufferedImage bi = new BufferedImage(captchaConfig.getWidth(), captchaConfig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        drawCaptcha(null, bi, captcha);
        
        if(captchaConfig.isDistortion()){
        	// 扭曲
        	//fast_shear(bi, captchaConfig.getTorsion());
        	bi = twistImage(bi);
        }
 
        try {
			ImageIO.write(bi, "png", os);  
		} catch (IOException e) {
			throw new SecurityException("图片返回流错误", e);
		}
        return captcha;
    }
	
	public String create2(String fontPath, ByteArrayOutputStream os) {
        String captcha = "";
        String captchaCharStr = captchaConfig.getCharacters();
        char[] captchaChars = captchaCharStr.toCharArray();
        for (int i = 0, length = captchaConfig.getCharNum(); i < length; i++) {
            captcha += captchaChars[random.nextInt(captchaChars.length)];
        }
 
        //BufferedImage bi = new BufferedImage(120, 55, BufferedImage.TYPE_INT_ARGB);
        BufferedImage bi = new BufferedImage(captchaConfig.getWidth(), captchaConfig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        drawCaptcha(fontPath, bi, captcha);
        
        if(captchaConfig.isDistortion()){
        	// 扭曲
        	//fast_shear(bi, captchaConfig.getTorsion());
        	bi = twistImage(bi);
        }
 
        try {
			ImageIO.write(bi, "png", os);  
		} catch (IOException e) {
			throw new SecurityException("图片返回流错误", e);
		}
        return captcha;
    }
	
	private void drawCaptcha(String fontPath, BufferedImage bi, String captcha) {
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 背景色
        g.setColor(ColorUtils.getColor(captchaConfig.getBgColor()));
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        
        String[] fontcolor = captchaConfig.getFontColor();
		List<Color> colorList = new ArrayList<>();
		if(fontcolor.length != 0){
			for(int i = 0; i < fontcolor.length; i++){
				if(StringUtils.isBlank(fontcolor[i])){
					throw new IllegalArgumentException("fontcolor 参数配置为空");
				}
				String colorStr = fontcolor[i];
				if(StringUtils.isBlank(colorStr)){
					throw new IllegalArgumentException("fontcolor 格式错误");
				}
				colorList.add(ColorUtils.getColor(colorStr.trim()));
			}
		}
        // 字体颜色
        g.setColor(colorList.get(new Random().nextInt(colorList.size())));
 
        int length = captcha.length();
        // 验证码字符串宽度
        // int fontWidth = g.getFontMetrics().stringWidth(captcha);
        // 验证码高度
        int fontHeight = g.getFontMetrics().getHeight();
        int imgHeight = captchaConfig.getHeight();
        // int imgWidth = captchaConfig.getWidth();
        // float sx = 2 + random.nextInt(3);
        float sx = 10;
        float sy = (imgHeight + fontHeight)/2 - random.nextInt(3);
        // 字体名称
        String fontName = captchaConfig.getFontfamily();
        
        Font font = null;
		if (StringUtils.isNotBlank(fontPath)) {
			font = FontLoader.loadFont(fontPath, fontName,captchaConfig.getFontSize());
		} else {
			font = new Font(fontName,Font.PLAIN, captchaConfig.getFontSize());
		}
        FontRenderContext frc = g.getFontRenderContext();
        Area area = new Area();
        for (int i = 0; i < length; i++) {
            GlyphVector glyphVector = font.createGlyphVector(frc, captcha.substring(i, i + 1));
            Shape glyph = glyphVector.getGlyphOutline(0, sx, sy);
            area.add(new Area(glyph));
            double w = glyph.getBounds2D().getMaxX() - glyph.getBounds2D().getMinX() + 15;
            sx +=  captchaConfig.getAdhesion() + w - (w / 50);
        }
        g.fill(area);
        g.dispose();
    }
	
	// 扭曲
	private void fast_shear(BufferedImage bi, int torsion) {
        Graphics2D g = bi.createGraphics();
        int w = bi.getWidth();
        int h = bi.getHeight();
 
        int ox = torsion + random.nextInt(11);
        int oy = torsion + random.nextInt(11);
        double sx = Math.PI * 2 * random.nextDouble() * 100;
        double sy = Math.PI * 2 * random.nextDouble() * 100;
        double rx = 0.5 + random.nextDouble() * 1.0;
        double rt = 1.5 - rx;
        double ry = rt / 3 + random.nextDouble() * rt / 3 * 2;
        double cx = w / rx / Math.PI / 2;
        double cy = h / ry / Math.PI / 2;
 
        Color bgColor = ColorUtils.getColor(captchaConfig.getBgColor());
        for (int i = 0; i < h; i++) {
            double d = (double) (ox >> 1) * Math.sin((double) i / (double) cx + sx);
            g.copyArea(0, i, w, 1, (int) d, 0);
            g.setColor(bgColor);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w, i, w, i);
        }
        for (int i = 0; i <= w; i++) {
            double d = (double) (oy >> 1) * Math.sin((double) i / (double) cy + sy);
            g.copyArea(i, 0, 1, h, 0, (int) d);
            g.setColor(bgColor);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h, i, h);
        }
        g.dispose();
    }
	
	/** 
     *  
     * @Description:正弦曲线Wave扭曲图片 
     * @since 1.0.0 
     * @Date:2012-3-1 下午12:49:47 
     * @return BufferedImage 
     */ 
    private BufferedImage twistImage(BufferedImage buffImg) {  
        double dMultValue = random.nextInt(7) + 3;// 波形的幅度倍数，越大扭曲的程序越高，一般为3  
        double dPhase = random.nextInt(6);// 波形的起始相位，取值区间（0-2＊PI）  
   
        BufferedImage destBi = new BufferedImage(buffImg.getWidth(),  
                buffImg.getHeight(), BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2d = destBi.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, buffImg.getWidth(), buffImg.getHeight());
        
        for (int i = 0; i < destBi.getWidth(); i++) {  
            for (int j = 0; j < destBi.getHeight(); j++) {  
                int nOldX = getXPosition4Twist(dPhase, dMultValue,  
                        destBi.getHeight(), i, j);  
                int nOldY = j;  
                if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0 
                        && nOldY < destBi.getHeight()) {  
                    destBi.setRGB(nOldX, nOldY, buffImg.getRGB(i, j));  
                }  
            }  
        }  
        return destBi;  
    } 
    
    /** 
     *  
     * @Description:获取扭曲后的x轴位置 
     * @since 1.0.0 
     * @Date:2012-3-1 下午3:17:53 
     * @param dPhase 
     * @param dMultValue 
     * @param height 
     * @param xPosition 
     * @param yPosition 
     * @return int 
     */ 
    private int getXPosition4Twist(double dPhase, double dMultValue,  
            int height, int xPosition, int yPosition) {  
        double PI = captchaConfig.getTorsion(); // 此值越大，扭曲程度越大   3.1415926535897932384626433832799
        double dx = (double) (PI * yPosition) / height + dPhase;  
        double dy = Math.sin(dx);  
        return xPosition + (int) (dy * dMultValue);  
    }

    public BufferedImage g1(){
        String captcha = "";
        String captchaCharStr = captchaConfig.getCharacters();
        char[] captchaChars = captchaCharStr.toCharArray();
        for (int i = 0, length = 4; i < length; i++) {
            captcha += captchaChars[random.nextInt(captchaChars.length)];
        }

        //BufferedImage bi = new BufferedImage(120, 55, BufferedImage.TYPE_INT_ARGB);
        BufferedImage bi = new BufferedImage(captchaConfig.getWidth(), captchaConfig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        drawCaptcha(null, bi, captcha);

        if(captchaConfig.isDistortion()){
            // 扭曲
            //fast_shear(bi, captchaConfig.getTorsion());
            bi = twistImage(bi);
        }
        return bi;
    }
}