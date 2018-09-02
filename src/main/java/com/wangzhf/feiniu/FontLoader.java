package com.wangzhf.feiniu;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * 字体加载类
 */
public class FontLoader {
	
	public static Map<File, Font> map = new HashMap<>();
	
	/**
	 * 加载java字体
	 * 
	 * @param fontFile       外部字体文件
	 * @param fontSize       字体大小
	 * @param Font
	 * @throws IOException
	 */
	public static Font loadFont(File fontFile, float fontSize) {
		try {
			Font dynamicFontPt = map.get(fontFile);
			if(dynamicFontPt == null){
				synchronized (FontLoader.class) {
					// 创建字体
					Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
					dynamicFontPt = dynamicFont.deriveFont(fontSize);
					map.put(fontFile, dynamicFontPt);
				}
			}
			return dynamicFontPt;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 加载java字体
	 * 
	 * @param fontPath       外部字体路径
	 * @param fontFileName   外部字体文件名
	 * @param fontSize       字体大小
	 * @param Font
	 * @throws IOException
	 */
	public static Font loadFont(String fontPath, String fontFileName, float fontSize) {
		return loadFont(new File(fontPath, fontFileName), fontSize);
	}
	
	/**
	 * 加载java字体(默认加载fonts文件下的字体文件)
	 * 
	 * @param fontPath       外部字体路径
	 * @param fontFileName   外部字体文件名
	 * @param fontSize       字体大小
	 * @param Font
	 * @throws IOException
	 */
	public static Font loadDefaultFolderFont(String classPath, String fontFileName, float fontSize) {
		return loadFont(new File(classPath + "/fonts" ,fontFileName), fontSize);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
	}
}