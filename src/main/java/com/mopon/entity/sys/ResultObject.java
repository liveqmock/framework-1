package com.mopon.entity.sys;

/**
 * <p>Description: 传输JSON对象</p>
 * @date 2013年9月27日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ResultObject<T> {
	
	/**
	 * 成功标志
	 */
	private boolean success;
	
	/**
	 * 失败消息
	 */
	private String message;
	
	/**
	 * 时间撮
	 */
	private long dateline = System.currentTimeMillis();
	
	/**
	 * 结果对象
	 */
	private T result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDateline() {
		return dateline;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
