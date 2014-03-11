package com.mopon.exception;


import com.mopon.entity.logs.ErrorMsg;

public class ErrorMsgException extends BaseException {

	private static final long serialVersionUID = -236344375375588995L;

	public ErrorMsgException(ErrorMsg errorMsg) {
		super(errorMsg.getMsg(), errorMsg);
	}
	
	public ErrorMsgException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public ErrorMsgException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public ErrorMsgException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
