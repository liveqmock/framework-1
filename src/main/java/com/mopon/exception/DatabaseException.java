package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

public class DatabaseException extends BaseException {

	private static final long serialVersionUID = 1224365525354974411L;

	public DatabaseException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public DatabaseException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public DatabaseException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	 
	public DatabaseException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}

}
