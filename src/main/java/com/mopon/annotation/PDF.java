package com.mopon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description:PDF视图注解，用于设置PDF生成的规则</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, 
		ElementType.TYPE})
public @interface PDF {

	/**
	 * 生成的文件名
	 */
	public String filename() default "";
	
	/**
	 * PDF类型：表格，文本，图片
	 */
	public PDFType type() default PDFType.TABLE;
	
	/**
	 * PDF的文本纸类型
	 */
	public PaperType paperType() default PaperType.A4;
	
	/**
	 * 顶部框
	 */
	public int borderTop() default 0;
	
	/**
	 * 低部框
	 */
	public int borderBottom() default 0;
	
	/**
	 * 左部框
	 */
	public int borderLeft() default 0;
	
	/**
	 * 右部框
	 */
	public int borderRight() default 0;
	
	/**
	 * 字体大小
	 */
	public int fontSize() default 12;
	
	/**
	 * 字体颜色
	 */
	public FontColor fontColor() default FontColor.BLACK;
	
	/**
	 * 字体样式
	 */
	public FontStyle fontStyle() default FontStyle.NORMAL;
	
	/**
	 * 表格列数
	 */
	public int tableRow() default 3;
	
	/**
	 * 表格头部字段名
	 */
	public String tableHeader() default "ROW1,ROW2,ROW3";
	
	/**
	 * PDF标题
	 */
	public String title() default "";
	
	/**
	 * PDF科目
	 */
	public String subject() default "";
	
	/**
	 * PDF作者
	 */
	public String author() default "";
	
	/**
	 * PDF关键字
	 */
	public String keywords() default "";
	
	/**
	 * PDF头部栏信息
	 */
	public String header() default "";
	
	/**
	 * PDF底部栏信息
	 */
	public String footer() default "";
	
	/**
	 * PDF图片类型的图片地址
	 */
	public String srcPath() default "";
	
	/**
	 * 图片宽度
	 */
	public int width() default 100;
	
	/**
	 * 图片高度
	 */
	public int height() default 100;
	
	/**
	 * 字段类型
	 */
	public FieldType fieldType() default FieldType.DOUBLE;

} 
