package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

public class ConfigException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ConfigException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public ConfigException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public ConfigException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public ConfigException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}

}
