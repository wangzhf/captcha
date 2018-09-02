package com.wangzhf.feiniu;

public class CaptchaConfig {
	
	// 验证码字体, 默认Verdana。    Serif, Sanserif, Monospace, Dialog, Verdana
	private String fontfamily = "Verdana";
	
	// 字体大小， 默认15
	private int fontSize = 15;
	
	// 文字颜色
	private String[] fontColor = new String[]{"2746A1", "C53326", "29A529"};
	
	// 背景颜色, 默认白色
	private String bgColor = "FFFFFF";
	
	// 验证码文字
	private String characters = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";
	
	// 字符个数， 默认为4
	private int charNum = 4;
	
	// 图片宽度
	private int width = 120;
	
	// 图片高度
	private int height = 32;
	
	// 扭曲度
	private float torsion = 5;
	
	// 粘连
	private int adhesion = 0;
	
	public CaptchaConfig(){
		
	}
	
	public static void main(String[] args) {
		//CaptchaConfig pacchaConfig = JSONObject.parseObject("{'width':'10'}", CaptchaConfig.class);
	}
	
	// 是否扭曲
	private boolean isDistortion = true;

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String[] getFontColor() {
		return fontColor;
	}

	public void setFontColor(String[] fontColor) {
		this.fontColor = fontColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public int getCharNum() {
		return charNum;
	}

	public void setCharNum(int charNum) {
		this.charNum = charNum;
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

	public boolean isDistortion() {
		return isDistortion;
	}

	public void setDistortion(boolean isDistortion) {
		this.isDistortion = isDistortion;
	}

	public String getFontfamily() {
		return fontfamily;
	}

	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}

	public float getTorsion() {
		return torsion;
	}

	public void setTorsion(float torsion) {
		this.torsion = torsion;
	}

	public int getAdhesion() {
		return adhesion;
	}

	public void setAdhesion(int adhesion) {
		this.adhesion = adhesion;
	}
}