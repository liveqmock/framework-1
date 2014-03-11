package com.mopon.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 * @date 2013年8月30日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("loggerUtil")
public class LoggerUtil {
	
	private Class<?> clazz;
	
	/**
	 * 日志对象
	 */
	private Logger logger = null;
	
	public LoggerUtil() {
		logger = Logger.getLogger(this.getClass());
	}
	
	public LoggerUtil(Class<?> clazz) {
		this.clazz = clazz;
		logger = Logger.getLogger(clazz);
	}
	
	/**
	 * 方法用途: 错误<br>
	 * 实现步骤: <br>
	 * @param message 日志内容
	 */
	public void error(String message) {
		logger.error(message);
	}
	
	/**
	 * 方法用途: 信息<br>
	 * 实现步骤: <br>
	 * @param message 日志内容
	 */
	public void info(String message) {
		logger.info(message);
	}
	
	/**
	 * 方法用途: 调试<br>
	 * 实现步骤: <br>
	 * @param message 日志内容
	 */
	public void debug(String message) {
		logger.debug(message);
	}
	
	/**
	 * 方法用途: 注意<br>
	 * 实现步骤: <br>
	 * @param message 日志内容
	 */
	public void warn(String message) {
		logger.warn(message);
	}
}
