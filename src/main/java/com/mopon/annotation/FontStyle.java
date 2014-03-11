package com.mopon.annotation;

/**
 * <p>Description:PDF字体样式，PDF生成时设定字体的样式</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum FontStyle {
	NORMAL(0), 
	BOLD(1), 
	ITALIC(2), 
	UNDERLINE(3), 
	STRIKETHRU(4), 
	BOLDITALIC(1|2);
	
	private FontStyle(int value) {
		this.value = value;
	}
	
	public int getFontStyleValue() {
		return this.value;
	}
	
	private int value;
}
