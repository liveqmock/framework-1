package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月23日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class GroupException extends BaseException {

	private static final long serialVersionUID = 55076438370256970L;

	public GroupException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public GroupException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public GroupException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public GroupException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
