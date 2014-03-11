package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

public class RoleException extends BaseException {

	private static final long serialVersionUID = 1L;

	public RoleException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public RoleException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public RoleException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public RoleException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}

}
