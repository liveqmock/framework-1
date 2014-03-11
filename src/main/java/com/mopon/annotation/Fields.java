package com.mopon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
/**
 * <p>Description:Google proto 字段注解，
 * 用于生成Google proto存根时对类的字段进
 * 行描述</p>
 * @date 2013年8月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Fields {
	/**
	 * proto字段生成形式  必须：required 可选：optional 集合：repeated 默认为required
	 */
	String fieldType() default "required";
	/**
	 * proto字段对应的java类的字段名默认为类字段名
	 */
	String fieldName() default "";
	/**
	 * java类型对应的protoType类型详细类型间Google buffer文档
	 */
	String protoType() default "";
	/**
	 * 嵌套其它的类的时候设置映射
	 */
	String mapping() default "";
	/**
	 * proto字段对应的序号默认-1自动生成
	 */
	int fieldIndex() default -1;
	/**
	 * proto字段对应的默认值
	 */
	String defValue() default "";
	/**
	 * 枚举类型定义
	 */
	String enums() default "";
}
