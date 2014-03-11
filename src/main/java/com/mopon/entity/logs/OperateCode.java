package com.mopon.entity.logs;

/**
 * <p>Description: 操作代码</p>
 * @date 2013年9月4日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public enum OperateCode {
	
	ADD(10001),
	
	EDIT(10002),
	
	QUERY(10003),
	
	VIEW(10004),
	
	POST(10005),
	
	DELETE(10006),
	
	BUY(10007),
	
	CANECL(10008),
	
	LOGIN(10009),
	
	LOGINOFF(20000),
	
	REGISTER(20001);
	
	private final int code;
	
	private OperateCode(int code) {
		this.code = code;
	}
	
	public int value() {
		return this.code;
	}
	
	public static OperateCode valueOf(int statusCode) {
		for (OperateCode status : values()) {
			if (status.code == statusCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}
