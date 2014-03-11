package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年9月24日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class FileException extends BaseException {

	private static final long serialVersionUID = -8411311683181001405L;

	public FileException(ErrorMsg errorMsg) {
		super(errorMsg.getMsg(), errorMsg);
	}
	
	public FileException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public FileException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public FileException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
