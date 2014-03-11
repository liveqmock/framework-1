package com.mopon.annotation;

import java.awt.Color;

/**
 * <p>Description:PDF生成时对字体颜色的描述枚举</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum FontColor {
	
	WHITE(255, 255, 255),
    LIGHT_GRAY(192, 192, 192),
    GRAY(128, 128, 128),
    DARK_GRAY(64, 64, 64),
    BLACK(0, 0, 0),
    RED(255, 0, 0),
    PINK(255, 175, 175),
    ORANGE(255, 200, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    MAGENTA(255, 0, 255), 
    CYAN(0, 255, 255),
    BLUE(0, 0, 255);
    
	/** 
	 * 方法用途: 构造方法
	 * 实现步骤: 通过颜色的RGB值创建枚举<br>
	 * @param int rv 红色
	 * @param int gv 绿色
	 * @param int bv 蓝色  
	 */
    private FontColor(int rv,int gv,int bv) {  
        this.redValue=rv;  
        this.greenValue=gv;  
        this.blueValue=bv;  
    }  
    
    /** 
	 * 方法用途: 得到颜色对象
	 * 实现步骤: 通过枚举类型的值创建颜色对象<br>
	 * 
	 */
    public Color getRGB() {
    	return new Color(this.redValue, this.greenValue, this.blueValue);
    }
    
    
    public String toString() {
    	return super.toString() + "R:" + redValue + " G:" + greenValue + " B:" + blueValue;
    }
    /**
	 * 红色值
	 */
	private int redValue;
	/**
	 * 绿色值
	 */
    private int greenValue;  
    /**
	 * 蓝色值
	 */
    private int blueValue;
    
}
