package com.wangzhf.feiniu;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

public class ColorUtils {
	
	public static Color getColor(String colorStr){
		if(StringUtils.isBlank(colorStr)){
			throw new IllegalArgumentException("fontcolor 参数配置为空");
		}
		
		String[] arr = colorStr.split(",");
		if(arr.length == 3){
			return new Color(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
		} else if(colorStr.length() == 6) {
			return ColorConvert.hx16ToColor(colorStr);
		} else {
			throw new IllegalArgumentException("fontcolor 参数错误");
		}
	}
	
	
}