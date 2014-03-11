package com.mopon.exception;

import com.mopon.entity.logs.ErrorMsg;

/**
 * <p>Description: </p>
 * @date 2013年8月23日
 * @author 罗浩
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class MenuItemException extends BaseException {

	private static final long serialVersionUID = -7826298495876684784L;

	public MenuItemException(ErrorMsg errorMsg) {
		super(errorMsg);
	}
	
	public MenuItemException(String message, ErrorMsg errorMsg) {
		super(message, errorMsg);
	}
	
	public MenuItemException(Throwable cause, ErrorMsg errorMsg) {
		super(cause, errorMsg);
	}
	
	public MenuItemException(String message, Throwable cause, ErrorMsg errorMsg) {
		super(message, cause, errorMsg);
	}
}
