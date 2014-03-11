package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月29日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class HttpFileException extends BaseException {

	private static final long serialVersionUID = 6722283073609826602L;

	public HttpFileException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public HttpFileException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public HttpFileException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public HttpFileException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
