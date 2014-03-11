package com.mopon.entity.logs;

/**
 * 
 * <p>Description: 日志级别</p>
 * @date 2013年9月5日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum ErrorLevel {

	/**
	 *	信息
	 */
	INFO(1),
	
	/**
	 *  调试
	 */
	DEBUG(2),
	
	/**
	 *  错误
	 */
	ERROR(3),
	
	/**
	 *  注意
	 */
	WARN(4);

	
	private final int type;
	
	private ErrorLevel(int type) {
		this.type = type;
	}
	
	public int value() {
		return this.type;
	}
	
	public static ErrorLevel valueOf(int type) {
		for (ErrorLevel status : values()) {
			if (status.type == type) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + type + "]");
	}
}
