package com.mopon.entity.sys;

import java.util.Map;

/**
 * <p>Description: </p>
 * @date 2013年10月10日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ResultMap {

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
	 * 结果集合
	 */
	private Map<String, Object> resultMap;

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

	public int getTotalCount() {
		return resultMap==null?0:resultMap.size();
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
}
