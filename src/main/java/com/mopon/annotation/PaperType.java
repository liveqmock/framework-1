package com.mopon.annotation;

import com.lowagie.text.Rectangle;
import com.lowagie.text.RectangleReadOnly;

/**
 * <p>Description:PDF页面设定，PDF生成时设定页面的纸类型</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum PaperType {
	A0(2384,3370), A1(1684,2384), A2(1191,1684), A3(842,1191), 
	A4(595,842), A5(420,595), A6(297,420), A7(210,297), A8(148,210), 
	A9(105,148), A10(73,105), B0(2834,4008), B1(2004,2834), B2(1417,2004), 
	B3(1000,1417), B4(708,1000), B5(498,708), B6(354,498), B7(249,354), B8(175,249), 
	B9(124,175), B10(87,124);
	private PaperType(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public String toString() {
		return super.toString() + "w:" + width + " h:" + height;
	}
	
	public Rectangle getRectangle() {
		return new RectangleReadOnly(this.width, this.height);
	}
	
	private int width;
	private int height;
}
