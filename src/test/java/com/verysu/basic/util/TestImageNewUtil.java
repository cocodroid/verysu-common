package com.verysu.basic.util;

import java.awt.Color;
import java.awt.Font;


public class TestImageNewUtil {
	private static String path = "C:\\Users\\Administrator\\Desktop\\2008319183523380_2.jpg";

	public static void main(String[] args) {
		//彩色转灰色
//		ImageNewUtils.imgTogray(path, "C:\\Users\\Administrator\\Desktop\\2008319183523380_2_gray.jpg");
		//格式转化
//		ImageNewUtils.convertType(path, ImageNewUtils.IMAGE_TYPE_GIF, "C:\\Users\\Administrator\\Desktop\\2008319183523380_2_convert.gif");
		//缩放，比例
//		ImageNewUtils.scaleRatio(path, "C:\\Users\\Administrator\\Desktop\\2008319183523380_2_scale2.jpg", 2, false);
		//缩放，高宽
//		ImageNewUtils.scaleHW(path, "C:\\Users\\Administrator\\Desktop\\2008319183523380_2_scale2hw.jpg", 300,120, false);
		//
		ImageNewUtils.textWatermark("你好！", path, "C:\\Users\\Administrator\\Desktop\\2008319183523380_2_waterText.jpg",
				"楷体",Font.BOLD, Color.yellow, 80, 10, 10, 0.85f);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
