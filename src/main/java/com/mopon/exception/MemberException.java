package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

public class MemberException extends BaseException {
	
	private static final long serialVersionUID = -6666158941258594942L;

	public MemberException(ErrorMsg errorMsg) {
		super(errorMsg.getMsg(), errorMsg);
	}
	
	public MemberException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public MemberException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public MemberException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
