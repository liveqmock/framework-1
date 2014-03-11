package com.mopon.entity.sys;

import java.util.List;
/**
 * <p>Description: 分页类</p>
 * @date 2013年9月23日
 * @author reagan
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class PageBean<T> {
	
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
	private long dateline;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 每页显示的结果数
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private long pageCount;

	/**
	 * 分页容器
	 */
	private List<T> dataList;
	
	/**
	 * 总记录数
	 */
	private long recordCount;

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

	public void setDateline(long dateline) {
		this.dateline = dateline;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long recordCount) {
		if(pageSize==0)return;
		if (recordCount % pageSize == 0) {
			this.pageCount = recordCount / pageSize;
		} else {
			this.pageCount = recordCount / pageSize + 1;
		}
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

}
