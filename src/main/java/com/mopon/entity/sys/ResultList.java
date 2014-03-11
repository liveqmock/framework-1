package com.mopon.entity.sys;

import java.util.List;

/**
 * <p>Description: 传输JSON列表对象</p>
 * @date 2013年9月27日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class ResultList<T> {

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
	private List<T> resultList;

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
		return resultList==null?0:resultList.size();
	}
	
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
}
