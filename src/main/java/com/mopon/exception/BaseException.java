package com.mopon.exception;

import com.mopon.asyn.LoggerAsynWrite;
import com.mopon.entity.logs.ErrorMsg;

public class BaseException extends Exception {

	private static final long serialVersionUID = 6730461614725855451L;

	protected ErrorMsg errorMsg;

	public ErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
		LoggerAsynWrite.addExceptionQueue(errorMsg);
	}
	
	public BaseException(ErrorMsg errorMsg) {
		super(errorMsg.getMsg());
		this.errorMsg = errorMsg;
		LoggerAsynWrite.addExceptionQueue(errorMsg);
	}
	
	public BaseException(String message, ErrorMsg errorMsg) {
		super(message);
		this.errorMsg = errorMsg;
		LoggerAsynWrite.addExceptionQueue(errorMsg);
	}
	
	public BaseException(Throwable cause, ErrorMsg errorMsg) {
		super(cause);
		this.errorMsg = errorMsg;
		LoggerAsynWrite.addExceptionQueue(errorMsg);
	}
	
	public BaseException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause);
		this.errorMsg = errorMsg;
		LoggerAsynWrite.addExceptionQueue(errorMsg);
	}
}
