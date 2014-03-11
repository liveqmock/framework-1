package com.mopon.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
/**
 * 类注解
 * 凡是被注解了@proto的类都会由程序生成为proto文件并编译为buffer类
 * @author reagan
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) 
public @interface Proto {
	/**
	 * 定义proto文件的包，该包是proto文件用来区分的命名空间与java类无关
	 */
	String protoPackage() default "";
	/**
	 * 定义生成类所要存放的包路径，默认为当前类所在的包（不推荐）
	 */
	String packageName() default "";
	/**
	 * 生成的类命名默认为类名，默认类名为类名+buffer
	 */
	String className() default "";
	/**
	 * 如果设置为真不会生成独立的proto文件
	 */
	boolean subClass() default false;
}
