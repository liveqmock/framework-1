package com.mopon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Description:EXCEL文档生成注解类</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, 
		ElementType.TYPE})
public @interface Xls {
	
	/**
	 * 模板地址
	 */
	public String templateName() default "";
	
	/**
	 * 开始行
	 */
	public int startRow() default 0;
	
	/**
	 * 文件名称
	 */
	public String fileName() default "";
	
	/**
	 * 字段类型
	 */
	public FieldType cellType() default FieldType.STRING;
	
	/**
	 * 是否加边框
	 */
	public boolean cellBorder() default true;
}
