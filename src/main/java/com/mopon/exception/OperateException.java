package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月28日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class OperateException extends BaseException {

	private static final long serialVersionUID = 9118183208202211482L;

	public OperateException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public OperateException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public OperateException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public OperateException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
