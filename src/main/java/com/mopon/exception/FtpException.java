package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月27日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class FtpException extends BaseException {

	private static final long serialVersionUID = -1721684527806519684L;

	public FtpException(ErrorMsg errorMsg) {
		super(errorMsg.getMsg(), errorMsg);
	}
	
	public FtpException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public FtpException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public FtpException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
