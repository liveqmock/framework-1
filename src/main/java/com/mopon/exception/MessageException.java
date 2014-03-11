package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月27日
 * @author Reading.Reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MessageException extends BaseException {

	private static final long serialVersionUID = 5762334258080382469L;

	public MessageException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public MessageException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public MessageException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public MessageException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
