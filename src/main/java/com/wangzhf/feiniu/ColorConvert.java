package com.wangzhf.feiniu;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class ColorConvert {

	/*
	 * 颜色：16进制转成RGB
	 * @param  strHxColor
	 */
	public static int[] hx16ToRGB(String strHxColor) {
		if(StringUtils.isBlank(strHxColor) || strHxColor.length() != 6){
			throw new IllegalArgumentException("strHxColor格式不正确");
		}
		
		int[] rgb = new int[3];
		rgb[0] = Integer.parseInt(strHxColor.substring(0, 2), 16);
		rgb[1] = Integer.parseInt(strHxColor.substring(2, 4), 16);
		rgb[2] = Integer.parseInt(strHxColor.substring(4, 6), 16);
		
		return rgb;
    }

	/*
	 * 颜色：16进制转成RGB颜色
	 */
	public static Color hx16ToColor(String strHxColor) {
		int[] rgbColor = hx16ToRGB(strHxColor);
		return new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
	}

	/*
	 * 颜色：RGB转成16进制
	 * @param  r
	 * @param  g
	 * @param  b
	 */
	public static String colorRGBtoHx16(int r, int g, int b) {
		return Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
	}
	
	public static void main(String[] args) {
		String strHxColor = "ABCDEF";
		int ph_t=Integer.parseInt(strHxColor,16);
		int[] rgb = new int[3];
		rgb[0] = Integer.parseInt(strHxColor.substring(0, 2), 16);
		rgb[1] = Integer.parseInt(strHxColor.substring(2, 4), 16);
		rgb[2] = Integer.parseInt(strHxColor.substring(4, 6), 16);
		Integer.toHexString(ph_t);
	}
}