package com.verysu.basic.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class ImageMagickUtil {

	private static ImageMagickUtil util;
	
	public static ImageMagickUtil getInstance(){
		if(util == null)
			util = new ImageMagickUtil();
		return util;
	}
	
	private ImageMagickUtil(){}
	
	private void setPath(ConvertCmd convert){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		// linux下不要设置此值，不然会报错  
		if(os.toLowerCase().startsWith("win")){
			convert.setSearchPath("C:\\Program Files\\ImageMagick-6.8.8-Q16");
		}
	}
	
	/**
	 * 根据坐标切图
	 * @param srcPath 要裁剪图片的路径 
	 * @param newPath 裁剪图片后的路径 
	 * @param x1 起始横坐标 
     * @param y1  起始纵坐标 
     * @param x2  结束横坐标 
	 * @param y2  结束纵坐标 
	 * @return
	 * @throws IM4JavaException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void cutImg(String srcPath, String newPath, int x1, int y1, int x2,   int y2) throws IOException, InterruptedException, IM4JavaException{
		int width = x2 - x1;
		int height = y2 - y1;
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		 /** 
         * width：  裁剪的宽度 
         * height： 裁剪的高度 
         * x1：       裁剪的横坐标 
         * y1：       裁剪的挫坐标 
         */
		op.crop(width, height, x1, y1);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd();  
	    setPath(convert);
	    convert.run(op);
	}
	
	 /** 
     *  
     * 根据尺寸缩放图片(缩略图)
     * @param width             缩放后的图片宽度 
     * @param height            缩放后的图片高度 
     * @param srcPath           源图片路径 
     * @param newPath           缩放后图片的路径 
     */  
    public void createThumb(Integer width, Integer height, String srcPath,  String newPath) throws Exception {  
        IMOperation op = new IMOperation();  
        op.addImage(srcPath);  
        op.resize(width, height);  
        op.addImage(newPath);  
        ConvertCmd convert = new ConvertCmd();  
        setPath(convert);
        convert.run(op);  
    }
}
